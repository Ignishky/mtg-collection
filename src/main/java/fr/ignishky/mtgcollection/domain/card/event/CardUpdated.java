package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.systemDefault;

public class CardUpdated extends Event<CardId, Card, CardUpdated.CardUpdatedPayload> {

    private final Price price;
    private final Boolean isOwned;
    private final Boolean isFoiled;

    public CardUpdated(CardId aggregateId, Price price, Boolean isOwned, Boolean isFoiled) {
        this(null, aggregateId, price, isOwned, isFoiled, Instants.now());
    }

    private CardUpdated(String id, CardId aggregateId, Price price, Boolean isOwned, Boolean isFoiled, Instant instant) {
        super(id, aggregateId, Card.class, new CardUpdatedPayload(price.eur(), price.eurFoil(), isOwned, isFoiled), instant);
        this.price = price;
        this.isOwned = isOwned;
        this.isFoiled = isFoiled;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregateId,
                aggregate.setCode(),
                aggregate.cardName(),
                aggregate.cardImage(),
                aggregate.prices().append(new Price(ofInstant(instant, systemDefault()), price.eur(), price.eurFoil())),
                isOwned,
                isFoiled
        );
    }

    record CardUpdatedPayload(
            String eur,
            String eurFoil,
            boolean isOwned,
            boolean isFoiled
    ) implements Payload {

    }

}
