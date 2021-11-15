package fr.ignishky.mtgcollection.framework.cqrs.event;

import io.vavr.collection.List;

public interface EventStore {

    void store(List<? extends Event<?, ?, ?>> events);

}
