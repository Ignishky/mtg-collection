package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.EventStore;
import io.vavr.control.Try;

public class EventPersistenceMiddleware extends CommandMiddleware {

    private final EventStore eventStore;

    private EventPersistenceMiddleware(CommandMiddleware next, EventStore eventStore) {
        super(next);
        this.eventStore = eventStore;
    }

    @Override
    public <T> Try<CommandResponse<T>> handle(Command<T> message) {
        var response = next(message);
        response.map(CommandResponse::events).onSuccess(eventStore::store);
        return response;
    }

    public record Builder(EventStore eventStore) implements CommandMiddlewareBuilder {

        @Override
        public CommandMiddleware chain(CommandMiddleware next) {
            return new EventPersistenceMiddleware(next, eventStore);
        }
    }
}
