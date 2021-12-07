package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

import java.time.Instant;

public class CardAdded extends Event<CardId, Card, CardAdded.Payload> {

    private final SetCode setCode;
    private final CardName name;
    private final CardImage image;

    public CardAdded(CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage) {
        this(null, aggregateId, setCode, cardName, cardImage, Instants.now());
    }

    private CardAdded(String id, CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, Instant instant) {
        super(id, aggregateId, Card.class, new Payload(cardName.name(), setCode.value(), cardImage.image()), instant);
        this.name = cardName;
        this.setCode = setCode;
        this.image = cardImage;
    }

    @Override
    public Card apply(Card aggregate) {
        return new Card(aggregateId(), setCode, name, image);
    }

    public record Payload(
            String name,
            String setCode,
            String image
    ) implements Event.Payload {
    }

}
