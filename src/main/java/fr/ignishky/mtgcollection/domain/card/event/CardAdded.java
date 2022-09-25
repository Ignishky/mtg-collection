package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.*;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.systemDefault;

public class CardAdded extends Event<CardId, Card, CardAdded.CardAddedPayload> {

    private final SetCode setCode;
    private final CardName cardName;
    private final CardImage cardImage;
    private final Price price;

    public CardAdded(CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, Price price) {
        this(null, aggregateId, setCode, cardName, cardImage, price, now());
    }

    private CardAdded(String id, CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, Price price, Instant instant) {
        super(id, aggregateId, Card.class, new CardAddedPayload(cardName.name(), setCode.value(), cardImage.image(), price.eur(), price.eurFoil()), instant);
        this.cardName = cardName;
        this.setCode = setCode;
        this.cardImage = cardImage;
        this.price = price;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregateId,
                setCode,
                cardName,
                cardImage,
                new Price(price.eur(), price.eurFoil()),
                false,
                false,
                ofInstant(instant, systemDefault())
        );
    }

    record CardAddedPayload(
            String name,
            String setCode,
            String image,
            Double eur,
            Double eurFoil
    ) implements Payload {

    }

}
