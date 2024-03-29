package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import com.google.gson.Gson;
import fr.ignishky.mtgcollection.domain.card.model.*;
import fr.ignishky.mtgcollection.domain.set.model.*;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.PriceRecord;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class MongoDocumentMapper {

    private static final Gson GSON = new Gson();

    public static SetDocument toDocument(Set set) {
        return new SetDocument(
                set.id().id(),
                set.code().value(),
                set.name().value(),
                set.parentSetCode().map(SetCode::value).getOrNull(),
                set.blockCode().map(SetCode::value).getOrNull(),
                set.releasedDate(),
                set.setType(),
                set.cardCount(),
                set.cardOwnedCount(),
                set.cardFullyOwnedCount(),
                set.icon().url()
        );
    }

    public static Set toSet(SetDocument document) {
        return new Set(
                new SetId(document.id()),
                new SetCode(document.code()),
                new SetName(document.name()),
                false,
                Option.of(document.parentSetCode()).map(SetCode::new),
                Option.of(document.blockCode()).map(SetCode::new),
                document.releaseDate(),
                document.setType(),
                document.cardCount(),
                document.cardOwnedCount(),
                document.cardFoilOwnedCount(),
                new SetIcon(document.icon())
        );
    }

    public static CardDocument toDocument(Card aCard) {
        return new CardDocument(
                aCard.id().id(),
                aCard.setCode().value(),
                aCard.cardName().name(),
                aCard.cardImage().image(),
                aCard.finishes().map(Finish::name).asJava(),
                new PriceRecord(aCard.prices().eur(), aCard.prices().eurFoil()),
                aCard.ownState(),
                aCard.lastUpdate()
        );
    }

    public static Card toCard(CardDocument document) {
        return new Card(
                new CardId(document.id()),
                new SetCode(document.setCode()),
                new CardName(document.name()),
                new CardImage(document.image()),
                List.ofAll(document.finishes()).map(Finish::valueOf),
                new Price(document.prices().eur(), document.prices().eurFoil()),
                document.ownState(),
                document.lastUpdate()
        );
    }

    static EventDocument fromEvent(Event<?, ?, ?> event) {
        return new EventDocument(
                event.id(),
                event.aggregateClass().getSimpleName(),
                event.aggregateId().toString(),
                event.getClass().getSimpleName(),
                GSON.toJson(event.payload()),
                event.instant()
        );
    }

}
