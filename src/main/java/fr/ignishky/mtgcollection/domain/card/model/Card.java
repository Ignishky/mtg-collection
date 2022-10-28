package fr.ignishky.mtgcollection.domain.card.model;

import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.event.CardRetired;
import fr.ignishky.mtgcollection.domain.card.event.CardUpdated;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.With;

import java.time.LocalDate;

import static fr.ignishky.mtgcollection.domain.card.model.Finish.NON_FOIL;
import static fr.ignishky.mtgcollection.domain.card.model.OwnState.FULL;
import static fr.ignishky.mtgcollection.domain.card.model.OwnState.PARTIAL;

@With
public record Card(
        CardId id,
        SetCode setCode,
        CardName cardName,
        CardImage cardImage,
        List<Finish> finishes,
        Price prices,
        OwnState ownState,
        LocalDate lastUpdate
) implements Aggregate<CardId> {

    public static AppliedEvent<Card, CardAdded> add(CardId cardId, SetCode setCode, CardName cardName, CardImage cardImage, List<Finish> finishes,
                                                    Price price) {
        var event = new CardAdded(cardId, setCode, cardName, cardImage, finishes, price);
        var card = event.apply(null);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardUpdated> update(CardId cardId, Price price) {
        var event = new CardUpdated(cardId, price);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardOwned> owned(OwnState ownState) {
        var event = new CardOwned(id, ownState);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public AppliedEvent<Card, CardRetired> retired() {
        var event = new CardRetired(id);
        var card = event.apply(this);
        return new AppliedEvent<>(card, event);
    }

    public boolean isOwned() {
        return ownState == PARTIAL || ownState == FULL;
    }

    public boolean isFullyOwned() {
        return ownState == FULL;
    }

    public double ownedPrice() {
        return ownState == PARTIAL || finishes.contains(NON_FOIL) ? prices.eur() : prices.eurFoil();
    }

    public Double fullPrice() {
        return Option.of(prices.eurFoil())
                .orElse(Option.of(prices.eur()))
                .getOrNull();
    }

}
