package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import com.google.gson.Gson;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
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

    private static final Gson GSON = new Gson();

    public static EventDocument fromEvent(Event<?, ?, ?> event) {
        return new EventDocument(
                event.id(),
                event.aggregateClass().getSimpleName(),
                event.aggregateId().toString(),
                event.getClass().getSimpleName(),
                GSON.toJson(event.payload()),
                event.instant()
        );
    }

}
