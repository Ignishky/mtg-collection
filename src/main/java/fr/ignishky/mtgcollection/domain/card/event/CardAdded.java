package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.*;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;
import io.vavr.collection.List;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.systemDefault;

public class CardAdded extends Event<CardId, Card, CardAdded.CardAddedPayload> {

    private final SetCode setCode;
    private final CardName cardName;
    private final CardImage cardImage;
    private final List<Finish> finishes;
    private final Price price;

    public CardAdded(CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, List<Finish> finishes, Price price) {
        this(null, aggregateId, setCode, cardName, cardImage, finishes, price, now());
    }

    private CardAdded(String id, CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, List<Finish> finishes,
                      Price price, Instant instant) {
        super(id, aggregateId, Card.class, new CardAddedPayload(cardName.name(), setCode.value(), cardImage.image(), finishes.asJava(), price.eur(), price.eurFoil()), instant);
        this.cardName = cardName;
        this.setCode = setCode;
        this.cardImage = cardImage;
        this.price = price;
        this.finishes = finishes;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(
                aggregateId,
                setCode,
                cardName,
                cardImage,
                finishes,
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
            java.util.List<Finish> finishes,
            Double eur,
            Double eurFoil
    ) implements Payload {

    }

}
