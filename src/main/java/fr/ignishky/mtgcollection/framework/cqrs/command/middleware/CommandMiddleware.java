package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.control.Try;

public abstract class CommandMiddleware {

    private final CommandMiddleware next;

    protected CommandMiddleware(CommandMiddleware next) {
        this.next = next;
    }

    public abstract <T> Try<CommandResponse<T>> handle(Command<T> message);

    protected <T> Try<CommandResponse<T>> next(Command<T> message) {
        return this.next.handle(message);
    }
}
