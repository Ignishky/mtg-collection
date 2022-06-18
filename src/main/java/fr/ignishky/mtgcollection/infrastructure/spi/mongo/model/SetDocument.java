package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.set.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("sets")
public record SetDocument(
        @Id
        UUID id,
        String code,
        String name,
        String releaseDate,
        int cardCount,
        String icon
) {

    public static SetDocument toSetDocument(Set set) {
        return new SetDocument(set.id().id(), set.code().value(), set.name().value(), set.releasedDate(), set.cardCount(), set.icon().url());
    }

    public static Set toSet(SetDocument document) {
        return new Set(new SetId(document.id), new SetCode(document.code), new SetName(document.name), false, document.releaseDate, document.cardCount(),
                new SetIcon(document.icon));
    }
}
