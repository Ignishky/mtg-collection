package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("events")
public record EventDocument(
        @Id
        String id,
        String aggregateName,
        String aggregateId,
        String name,
        String payload,
        Instant instant
) {

}
