package fr.ignishky.mtgcollection.domain.card.event;

import fr.ignishky.mtgcollection.domain.card.*;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;
import io.vavr.collection.List;

import java.time.Instant;

import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.systemDefault;

public class CardAdded extends Event<CardId, Card, CardAdded.CardAddedPayload> {

    private final SetCode setCode;
    private final CardName cardName;
    private final CardImage cardImage;
    private final List<Price> prices;

    public CardAdded(CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, List<Price> prices) {
        this(null, aggregateId, setCode, cardName, cardImage, prices, Instants.now());
    }

    private CardAdded(String id, CardId aggregateId, SetCode setCode, CardName cardName, CardImage cardImage, List<Price> prices, Instant instant) {
        super(id, aggregateId, Card.class, new CardAddedPayload(cardName.name(), setCode.value(), cardImage.image(), prices.head().eur(), prices.head().eurFoil()), instant);
        this.cardName = cardName;
        this.setCode = setCode;
        this.cardImage = cardImage;
        this.prices = prices;
    }

    @Override
    public Card apply(Card aggregate) {
        List<Price> priceList = aggregate == null ? List.empty() : aggregate.prices();
        return new Card(aggregateId,
                setCode,
                cardName,
                cardImage,
                priceList.append(new Price(ofInstant(instant, systemDefault()), prices.head().eur(), prices.head().eurFoil())),
                false,
                false);
    }

    record CardAddedPayload(
            String name,
            String setCode,
            String image,
            String eur,
            String eurFoil
    ) implements Payload {

    }

}
