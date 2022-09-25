package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class CardRetired extends Event<CardId, Card, CardRetired.CardRetiredPayload> {

    public CardRetired(CardId aggregateId) {
        this(null, aggregateId, now());
    }

    private CardRetired(String id, CardId aggregateId, Instant instant) {
        super(id, aggregateId, Card.class, new CardRetired.CardRetiredPayload(), instant);
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregate.id(),
                aggregate.setCode(),
                aggregate.cardName(),
                aggregate.cardImage(),
                aggregate.prices(),
                false,
                false,
                aggregate.lastUpdate()
        );
    }

    record CardRetiredPayload() implements Payload {

    }

}
