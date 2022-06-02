package fr.ignishky.mtgcollection.framework.cqrs.event;

import fr.ignishky.mtgcollection.framework.domain.Aggregate;

import java.time.Instant;

public abstract class Event<I, A extends Aggregate<I>, P extends Event.Payload> {

    private final String id;
    private final I aggregateId;
    private final Class<A> aggregateClass;
    private final P payload;
    private final Instant instant;

    protected Event(String id, I aggregateId, Class<A> aggregateClass, P payload, Instant instant) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.aggregateClass = aggregateClass;
        this.payload = payload;
        this.instant = instant;
    }

    public String id() {
        return id;
    }

    public I aggregateId() {
        return aggregateId;
    }

    public Class<A> aggregateClass() {
        return aggregateClass;
    }

    public Instant instant() {
        return instant;
    }

    public P payload() {
        return payload;
    }

    public abstract A apply(A aggregate);

    public interface Payload {
    }

}
