package fr.ignishky.mtgcollection.domain;

import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;

public record AppliedEvent<A extends Aggregate<?>, E extends Event<?, A, ?>> (
        A aggregate,
        E event
) {
}
