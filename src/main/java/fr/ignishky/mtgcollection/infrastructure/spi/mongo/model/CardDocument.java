package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.card.Card;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("cards")
public record CardDocument(
        @Id
        UUID id,
        String setCode,
        String name,
        String image
) {

    public static CardDocument fromCard(Card card) {
        return new CardDocument(card.id().id(), card.setCode().value(), card.cardName().name(), card.cardImage().image());
    }

}
