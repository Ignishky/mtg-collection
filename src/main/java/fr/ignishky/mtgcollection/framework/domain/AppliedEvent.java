package fr.ignishky.mtgcollection.framework.domain;

import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

public record AppliedEvent<A extends Aggregate<?>, E extends Event<?, A, ?>>(
        A aggregate,
        E event
) {

}
