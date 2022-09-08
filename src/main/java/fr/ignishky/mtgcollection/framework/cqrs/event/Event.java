package fr.ignishky.mtgcollection.framework.cqrs.event;

import fr.ignishky.mtgcollection.framework.domain.Aggregate;

import java.time.Instant;

public abstract class Event<I, A extends Aggregate<I>, P extends Payload> {

    protected final String id;
    protected final I aggregateId;
    private final Class<A> aggregateClass;
    protected final P payload;
    protected final Instant instant;

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

}
