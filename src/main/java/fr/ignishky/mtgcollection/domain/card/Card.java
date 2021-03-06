package fr.ignishky.mtgcollection.domain.card;

import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;

public record Card(
        CardId id,
        SetCode setCode,
        CardName cardName,
        CardImage cardImage,
        boolean isOwned,
        boolean isFoiled
) implements Aggregate<CardId> {

    public static AppliedEvent<Card, CardAdded> add(CardId cardId, SetCode setCode, CardName cardName, CardImage cardImage) {
        CardAdded event = new CardAdded(cardId, setCode, cardName, cardImage);
        Card card = event.apply(null);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardOwned> owned(boolean isFoiled) {
        CardOwned event = new CardOwned(id, true, isFoiled);
        Card card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

}
