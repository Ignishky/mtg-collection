package fr.ignishky.mtgcollection.framework.cqrs.command;

import fr.ignishky.mtgcollection.framework.cqrs.command.middleware.CommandMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.command.middleware.CommandMiddlewareBuilder;
import io.vavr.collection.Set;
import io.vavr.control.Try;

public class DirectCommandBus implements CommandBus {

    private final CommandMiddleware chain;

    public DirectCommandBus(Set<CommandMiddlewareBuilder> builders) {
        this.chain = CommandMiddlewareBuilder.build(builders);
    }

    @Override
    public <T> Try<T> dispatch(Command<T> message) {
        return chain.handle(message).map(CommandResponse::value);
    }

}
