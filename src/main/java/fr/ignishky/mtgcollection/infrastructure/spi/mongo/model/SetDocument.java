package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.set.SetType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("sets")
public record SetDocument(
        @Id
        UUID id,
        String code,
        String name,
        String parentSetCode,
        String blockCode,
        String releaseDate,
        SetType setType,
        int cardCount,
        String icon
) {

}
