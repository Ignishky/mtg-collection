package fr.ignishky.mtgcollection.framework.cqrs.event;

import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("aggregateId", aggregateId)
                .append("aggregateClass", aggregateClass)
                .append("payload", payload)
                .append("instant", instant)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Event<?, ?, ?> event = (Event<?, ?, ?>) o;

        return new EqualsBuilder()
                .append(id, event.id)
                .append(aggregateId, event.aggregateId)
                .append(aggregateClass, event.aggregateClass)
                .append(payload, event.payload)
                .append(instant, event.instant)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(aggregateId)
                .append(aggregateClass)
                .append(payload)
                .append(instant)
                .toHashCode();
    }

    public interface Payload {
    }

}
