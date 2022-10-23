package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class CardOwned extends Event<CardId, Card, CardOwned.CardOwnedPayload> {

    private final boolean isOwned;
    private final boolean isOwnedFoil;

    public CardOwned(CardId aggregateId, boolean isOwned, boolean isOwnedFoil) {
        this(null, aggregateId, isOwned, isOwnedFoil, now());
    }

    private CardOwned(String id, CardId aggregateId, boolean isOwned, boolean isOwnedFoil, Instant instant) {
        super(id, aggregateId, Card.class, new CardOwnedPayload(isOwned, isOwnedFoil), instant);
        this.isOwned = isOwned;
        this.isOwnedFoil = isOwnedFoil;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregate.id(),
                aggregate.setCode(),
                aggregate.cardName(),
                aggregate.cardImage(),
                aggregate.finishes(),
                aggregate.prices(),
                isOwned,
                isOwnedFoil,
                aggregate.lastUpdate());
    }

    record CardOwnedPayload(
            boolean isOwned,
            boolean isOwnedFoil
    ) implements Payload {

    }

}
