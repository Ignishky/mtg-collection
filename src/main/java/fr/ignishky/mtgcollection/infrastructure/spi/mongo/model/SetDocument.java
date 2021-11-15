package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.set.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("sets")
public record SetDocument(
        @Id
        UUID id,
        String code,
        String name
) {

        public static SetDocument fromSet(Set set) {
                return new SetDocument(set.id().id(), set.code().code(), set.name().name());
        }

}
