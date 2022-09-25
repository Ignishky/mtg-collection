package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

public class CardUpdated extends Event<CardId, Card, CardUpdated.CardUpdatedPayload> {

    private final Price price;

    public CardUpdated(CardId aggregateId, Price price) {
        this(null, aggregateId, price, Instants.now());
    }

    private CardUpdated(String id, CardId aggregateId, Price price, Instant instant) {
        super(id, aggregateId, Card.class, new CardUpdatedPayload(price.eur(), price.eurFoil()), instant);
        this.price = price;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregateId,
                aggregate.setCode(),
                aggregate.cardName(),
                aggregate.cardImage(),
                new Price(price.eur(), price.eurFoil()),
                aggregate.isOwned(),
                aggregate.isFoiled()
        );
    }

    record CardUpdatedPayload(
            String eur,
            String eurFoil
    ) implements Payload {

    }

}
