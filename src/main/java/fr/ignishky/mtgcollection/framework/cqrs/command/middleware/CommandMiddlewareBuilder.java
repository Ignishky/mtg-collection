package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Try;

public interface CommandMiddlewareBuilder {

    static CommandMiddleware build(Set<? extends CommandMiddlewareBuilder> builders) {
        return List.ofAll(builders).foldRight(new CircuitBreakerMiddleware(), CommandMiddlewareBuilder::chain);
    }

    CommandMiddleware chain(CommandMiddleware next);

    class CircuitBreakerMiddleware extends CommandMiddleware {

        CircuitBreakerMiddleware() {
            super(null);
        }

        @Override
        public <T> Try<CommandResponse<T>> handle(Command<T> command) {
            throw new IllegalStateException("No final command middleware provided in the chain");
        }

    }

}
