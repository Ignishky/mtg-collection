package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetId;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class SetUpdated extends Event<SetId, Set, SetUpdated.SetUpdatedPayload> {

    private final int cardOwnedCount;

    public SetUpdated(SetId aggregateId, int cardOwnedCount) {
        this(null, aggregateId, cardOwnedCount, now());
    }

    private SetUpdated(String id, SetId aggregateId, int cardOwnedCount, Instant instant) {
        super(id, aggregateId, Set.class, new SetUpdated.SetUpdatedPayload(cardOwnedCount), instant);
        this.cardOwnedCount = cardOwnedCount;
    }

    @Override
    public Set apply(Set aggregate) {
        return new Set(
                aggregate.id(),
                aggregate.code(),
                aggregate.name(),
                aggregate.isDigital(),
                aggregate.parentSetCode(),
                aggregate.blockCode(),
                aggregate.releasedDate(),
                aggregate.setType(),
                aggregate.cardCount(),
                cardOwnedCount,
                aggregate.icon()
        );
    }

    record SetUpdatedPayload(
            int cardOwnedCount
    ) implements Payload {

    }

}
