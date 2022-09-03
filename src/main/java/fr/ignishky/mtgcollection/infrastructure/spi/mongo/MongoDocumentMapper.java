package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import com.google.gson.Gson;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.control.Option;

public class MongoDocumentMapper {

    private static final Gson GSON = new Gson();

    public static SetDocument toSetDocument(Set set) {
        return new SetDocument(set.id().id(),
                set.code().value(),
                set.name().value(),
                set.parentSetCode().map(SetCode::value).getOrNull(),
                set.blockCode().map(SetCode::value).getOrNull(),
                set.releasedDate(),
                set.setType(),
                set.cardCount(),
                set.icon().url());
    }

    public static Set toSet(SetDocument document) {
        return new Set(new SetId(document.id()),
                new SetCode(document.code()),
                new SetName(document.name()),
                false,
                Option.of(document.parentSetCode()).map(SetCode::new),
                Option.of(document.blockCode()).map(SetCode::new),
                document.releaseDate(),
                document.setType(),
                document.cardCount(),
                new SetIcon(document.icon()));
    }

    public static CardDocument toCardDocument(Card aCard) {
        return new CardDocument(aCard.id().id(),
                aCard.setCode().value(),
                aCard.cardName().name(),
                aCard.cardImage().image(),
                aCard.isOwned(),
                aCard.isFoiled()
        );
    }

    public static Card toCard(CardDocument document) {
        return new Card(new CardId(document.id()),
                new SetCode(document.setCode()),
                new CardName(document.name()),
                new CardImage(document.image()),
                document.inCollection(),
                document.isFoiled()
        );
    }

    public static EventDocument fromEvent(Event<?, ?, ?> event) {
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
