package fr.ignishky.mtgcollection.domain.card;

import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.event.CardRetired;
import fr.ignishky.mtgcollection.domain.card.event.CardUpdated;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.collection.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public record Card(
        CardId id,
        SetCode setCode,
        CardName cardName,
        CardImage cardImage,
        List<Price> prices,
        boolean isOwned,
        boolean isFoiled
) implements Aggregate<CardId> {

    public static AppliedEvent<Card, CardAdded> add(CardId cardId, SetCode setCode, CardName cardName, CardImage cardImage, Price price) {
        var event = new CardAdded(cardId, setCode, cardName, cardImage, price);
        var card = event.apply(null);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardUpdated> update(CardId cardId, Price price, Boolean isOwned, Boolean isFoiled) {
        var event = new CardUpdated(cardId, price, isOwned, isFoiled);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardOwned> owned(boolean isFoiled) {
        var event = new CardOwned(id, true, isFoiled);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardRetired> retired() {
        var event = new CardRetired(id);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public boolean hasPrice() {
        return prices.nonEmpty() && isNotBlank(prices.head().eur()) || isNotBlank(prices.head().eurFoil());
    }

}
