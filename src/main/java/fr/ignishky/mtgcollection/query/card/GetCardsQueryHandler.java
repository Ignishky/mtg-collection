package fr.ignishky.mtgcollection.query.card;

import fr.ignishky.mtgcollection.domain.card.Card;
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
public class GetCardsQueryHandler implements QueryHandler<GetCardsQuery, GetCardsResponse> {

    private final MongoTemplate mongoTemplate;

    public GetCardsQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public GetCardsResponse handle(GetCardsQuery query) {
        List<Card> cards = retrieveCardsList(query);
        return new GetCardsResponse(
                retrieveSetName(query),
                cards.count(Card::isOwned),
                cards.count(Card::isFoiled),
                cards.map(Card::prices).map(price -> price.eurFoil() != null ? price.eurFoil() : price.eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards.filter(Card::isOwned).map(card -> card.isFoiled() ? card.prices().eurFoil() : card.prices().eur()).filter(Objects::nonNull).sum().doubleValue(),
                cards
        );
    }

    private Option<String> retrieveSetName(GetCardsQuery query) {
        return query.code()
                .map(setCode -> new Query().addCriteria(where("code").is(setCode.value())))
                .flatMap(setQuery -> Option.of(mongoTemplate.findOne(setQuery, SetDocument.class)))
                .map(SetDocument::name);
    }

    private List<Card> retrieveCardsList(GetCardsQuery query) {
        return query.code()
                .map(setCode -> new Query().addCriteria(where("setCode").is(setCode.value())))
                .orElse(Option.of(new Query().addCriteria(where("inCollection").is(query.owned()))))
                .transform(cardQuery -> List.ofAll(mongoTemplate.find(cardQuery.get(), CardDocument.class)))
                .map(MongoDocumentMapper::toCard);
    }

}
