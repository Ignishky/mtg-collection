package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.CardId;
import fr.ignishky.mtgcollection.domain.card.model.OwnState;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class CardOwned extends Event<CardId, Card, CardOwned.CardOwnedPayload> {

    private final OwnState ownState;

    public CardOwned(CardId aggregateId, OwnState ownState) {
        this(null, aggregateId, ownState, now());
    }

    private CardOwned(String id, CardId aggregateId, OwnState ownState, Instant instant) {
        super(id, aggregateId, Card.class, new CardOwnedPayload(ownState), instant);
        this.ownState = ownState;
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
                ownState,
                aggregate.lastUpdate());
    }

    record CardOwnedPayload(
            OwnState ownState
    ) implements Payload {

    }

}
