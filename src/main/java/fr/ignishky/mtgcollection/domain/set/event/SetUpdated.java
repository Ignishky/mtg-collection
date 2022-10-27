package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.model.Set;
import fr.ignishky.mtgcollection.domain.set.model.SetId;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class SetUpdated extends Event<SetId, Set, SetUpdated.SetUpdatedPayload> {

    private final int cardOwnedCount;
    private final int cardFoilOwnedCount;

    public SetUpdated(SetId aggregateId, int cardOwnedCount, int cardFoilOwnedCount) {
        this(null, aggregateId, cardOwnedCount, cardFoilOwnedCount, now());
    }

    private SetUpdated(String id, SetId aggregateId, int cardOwnedCount, int cardFoilOwnedCount, Instant instant) {
        super(id, aggregateId, Set.class, new SetUpdatedPayload(cardOwnedCount, cardFoilOwnedCount), instant);
        this.cardOwnedCount = cardOwnedCount;
        this.cardFoilOwnedCount = cardFoilOwnedCount;
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
                cardFoilOwnedCount,
                aggregate.icon()
        );
    }

    record SetUpdatedPayload(
            int cardOwnedCount,
            int cardFoilOwnedCount
    ) implements Payload {

    }

}
