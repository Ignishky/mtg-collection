package fr.ignishky.mtgcollection.domain.collection.query;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.collection.Collection;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class GetCollectionQueryHandler implements QueryHandler<GetCollectionQuery, Collection> {

    private final MongoTemplate mongoTemplate;

    public GetCollectionQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Collection handle(GetCollectionQuery query) {
        List<Card> cards = retrieveCardsList();
        return new Collection(
                cards.count(Card::isOwned),
                cards.count(Card::isOwnedFoil),
                cards.map(Card::prices).map(price -> price.eurFoil() != null ? price.eurFoil() : price.eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards.filter(Card::isOwned).map(card -> card.isOwnedFoil() ? card.prices().eurFoil() : card.prices().eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards
        );
    }

    private List<Card> retrieveCardsList() {
        Query query = new Query().addCriteria(where("inCollection").is(true));
        return List.ofAll(mongoTemplate.find(query, CardDocument.class))
                .map(MongoDocumentMapper::toCard);
    }

}
