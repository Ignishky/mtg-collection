package fr.ignishky.mtgcollection.domain;

import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;

public record AppliedEvent<AGGREGATE extends Aggregate<?>, EVENT extends Event<?, AGGREGATE, ?>> (
        AGGREGATE aggregate,
        EVENT event
) {
}
