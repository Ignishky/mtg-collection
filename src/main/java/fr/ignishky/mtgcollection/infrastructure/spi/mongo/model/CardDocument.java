package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("cards")
public record CardDocument(
        @Id
        UUID id,
        String setCode,
        String name,
        String image,
        PriceRecord prices,
        boolean inCollection,
        boolean isFoiled
) {

}
