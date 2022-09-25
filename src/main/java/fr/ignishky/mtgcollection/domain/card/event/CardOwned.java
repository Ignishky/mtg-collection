package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class CardOwned extends Event<CardId, Card, CardOwned.CardOwnedPayload> {

    private final boolean isOwned;
    private final boolean isFoiled;

    public CardOwned(CardId aggregateId, boolean isOwned, boolean isFoiled) {
        this(null, aggregateId, isOwned, isFoiled, now());
    }

    private CardOwned(String id, CardId aggregateId, boolean isOwned, boolean isFoiled, Instant instant) {
        super(id, aggregateId, Card.class, new CardOwnedPayload(isOwned, isFoiled), instant);
        this.isOwned = isOwned;
        this.isFoiled = isFoiled;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregate.id(),
                aggregate.setCode(),
                aggregate.cardName(),
                aggregate.cardImage(),
                aggregate.prices(),
                isOwned,
                isFoiled,
                aggregate.lastUpdate());
    }

    record CardOwnedPayload(
            boolean isOwned,
            boolean isFoiled
    ) implements Payload {

    }

}
