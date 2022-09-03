package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.EventStore;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoEventStore implements EventStore {

    private final MongoTemplate mongoTemplate;

    public MongoEventStore(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void store(List<? extends Event<?, ?, ?>> events) {
        events.map(MongoDocumentMapper::fromEvent)
                .forEach(mongoTemplate::save);
    }

}
