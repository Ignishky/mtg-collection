package fr.ignishky.mtgcollection.domain.set.query;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.exception.SetNotFoundException;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class GetSetQueryHandler implements QueryHandler<GetSetQuery, GetSetResponse> {

    private final MongoTemplate mongoTemplate;

    public GetSetQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public GetSetResponse handle(GetSetQuery query) {
        Option<Set> setOption = retrieveSet(query);
        if (setOption.isEmpty()) {
            throw new SetNotFoundException();
        }

        List<Card> cards = retrieveCardsFromSet(setOption.get());
        return new GetSetResponse(
                setOption.get().name().value(),
                cards.count(Card::isOwned),
                cards.count(Card::isOwnedFoil),
                cards.map(Card::prices).map(price -> price.eurFoil() != null ? price.eurFoil() : price.eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards.filter(Card::isOwned).map(card -> card.isOwnedFoil() ? card.prices().eurFoil() : card.prices().eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards
        );
    }

    private Option<Set> retrieveSet(GetSetQuery getSetQuery) {
        Query query = new Query().addCriteria(where("code").is(getSetQuery.code().value()));
        return Option.of(mongoTemplate.findOne(query, SetDocument.class))
                .map(MongoDocumentMapper::toSet);
    }

    private List<Card> retrieveCardsFromSet(Set set) {
        Query query = new Query().addCriteria(where("setCode").is(set.code().value()));
        return List.ofAll(mongoTemplate.find(query, CardDocument.class))
                .map(MongoDocumentMapper::toCard);
    }

}
