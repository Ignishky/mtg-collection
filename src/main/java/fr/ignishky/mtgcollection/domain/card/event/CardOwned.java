package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

import java.time.Instant;

public class CardOwned extends Event<CardId, Card, CardOwned.Payload> {

    private final Boolean isOwned;
    private final Boolean isFoiled;

    public CardOwned(CardId aggregateId, Boolean isOwned, Boolean isFoiled) {
        this(null, aggregateId, isOwned, isFoiled, Instants.now());
    }

    private CardOwned(String id, CardId aggregateId, Boolean isOwned, Boolean isFoiled, Instant instant) {
        super(id, aggregateId, Card.class, new Payload(isOwned, isFoiled), instant);
        this.isOwned = isOwned;
        this.isFoiled = isFoiled;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(aggregate.id(), aggregate.setCode(), aggregate.cardName(), aggregate.cardImage(), isOwned, isFoiled);
    }

    public record Payload(
            Boolean isOwned,
            Boolean isFoiled
    ) implements Event.Payload {
    }
}
