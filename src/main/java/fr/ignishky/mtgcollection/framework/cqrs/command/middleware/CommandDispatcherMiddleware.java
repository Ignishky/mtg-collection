package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;

import java.util.Set;

public class CommandDispatcherMiddleware extends CommandMiddleware {

    private final Map<Class<? extends Command>, CommandHandler> handlers;

    private CommandDispatcherMiddleware(CommandMiddleware next, List<CommandHandler<?, ?>> handlers) {
        super(next);
        this.handlers = handlers.toMap(handler -> Tuple.of(handler.listenTo(), handler));
    }

    @Override
    public <T> Try<CommandResponse<T>> handle(Command<T> command) {
        return handlers.get(command.getClass())
                .map(handler -> Try.of(() -> (CommandResponse<T>) handler.handle(command)))
                .getOrElse(Try.failure(new IllegalArgumentException("command handler not found for %s".formatted(command.getClass()))));
    }

    public record Builder(
            Set<CommandHandler<?, ?>> handlers
    ) implements CommandMiddlewareBuilder {

        @Override
        public CommandDispatcherMiddleware chain(CommandMiddleware next) {
            return new CommandDispatcherMiddleware(next, List.ofAll(handlers));
        }

    }

}
