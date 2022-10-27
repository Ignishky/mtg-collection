package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.domain.set.model.SetType;
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
        Integer cardCount,
        Integer cardOwnedCount,
        Integer cardFoilOwnedCount,
        String icon
) {

    public static SetDocument from(SetCode code) {
        return new SetDocument(null, code.value(), null, null, null, null, null, null, null, null, null);
    }

}
