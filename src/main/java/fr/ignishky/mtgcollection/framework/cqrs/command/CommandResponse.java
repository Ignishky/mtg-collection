package fr.ignishky.mtgcollection.framework.cqrs.command;

import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import io.vavr.collection.List;

public record CommandResponse<T>(
        T value,
        List<? extends Event<?, ?, ?>> events
) {

    public static <T> CommandResponse<T> toCommandResponse(List<? extends Event<?, ?, ?>> events) {
        return new CommandResponse<>(null, events);
    }

}
