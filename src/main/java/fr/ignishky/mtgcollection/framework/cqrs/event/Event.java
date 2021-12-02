package fr.ignishky.mtgcollection.framework.cqrs.event;

import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@ToString
@EqualsAndHashCode
public abstract class Event<ID, AGGREGATE extends Aggregate<ID>, PAYLOAD extends Event.Payload> {

    private final String id;
    private final ID aggregateId;
    private final Class<AGGREGATE> aggregateClass;
    private final PAYLOAD payload;
    private final Instant instant;

    protected Event(String id, ID aggregateId, Class<AGGREGATE> aggregateClass, PAYLOAD payload, Instant instant) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.aggregateClass = aggregateClass;
        this.payload = payload;
        this.instant = instant;
    }

    public String id() {
        return id;
    }

    public ID aggregateId() {
        return aggregateId;
    }

    public Class<AGGREGATE> aggregateClass() {
        return aggregateClass;
    }

    public Instant instant() {
        return instant;
    }

    public PAYLOAD payload() {
        return payload;
    }

    public abstract AGGREGATE apply(AGGREGATE aggregate);

    public interface Payload {
    }

}
