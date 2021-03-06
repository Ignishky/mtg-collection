package fr.ignishky.mtgcollection.query.card;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class GetCardsQueryHandler implements QueryHandler<GetCardsQuery, List<Card>> {

    private final MongoTemplate mongoTemplate;

    public GetCardsQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Card> handle(GetCardsQuery query) {
        var mongoQuery = query.code()
                .map(setCode -> new Query().addCriteria(where("setCode").is(setCode.value())))
                .getOrElse(new Query().addCriteria(where("inCollection").is(query.owned())));

        return List.ofAll(mongoTemplate.find(mongoQuery, CardDocument.class))
                .map(CardDocument::toCard);
    }

}
