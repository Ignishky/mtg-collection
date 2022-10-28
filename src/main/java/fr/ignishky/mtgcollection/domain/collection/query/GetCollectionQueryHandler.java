package fr.ignishky.mtgcollection.domain.collection.query;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.collection.model.Collection;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static fr.ignishky.mtgcollection.domain.card.model.OwnState.NONE;
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
                cards.count(Card::isFullyOwned),
                cards.map(Card::fullPrice).filter(Objects::nonNull).sum().doubleValue(),
                cards.filter(Card::isOwned).map(Card::ownedPrice).filter(Objects::nonNull).sum().doubleValue(),
                cards
        );
    }

    private List<Card> retrieveCardsList() {
        Query query = new Query().addCriteria(where("ownState").ne(NONE));
        return List.ofAll(mongoTemplate.find(query, CardDocument.class))
                .map(MongoDocumentMapper::toCard);
    }

}
