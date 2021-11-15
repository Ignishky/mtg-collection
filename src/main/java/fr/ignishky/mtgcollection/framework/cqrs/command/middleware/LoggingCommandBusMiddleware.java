package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingCommandBusMiddleware extends CommandMiddleware {

    public LoggingCommandBusMiddleware(CommandMiddleware next) {
        super(next);
    }

    @Override
    public <T> Try<CommandResponse<T>> handle(Command<T> message) {
        log.info("Executing {} with parameter {}", message.getClass().getSimpleName(), message);
        return next(message)
                .onSuccess(s -> log.info("Success on {}. Result : {}, Events : {}", message.getClass().getSimpleName(), s.value(), s.events()))
                .onFailure(f -> log.error("Error on {}", message.getClass().getSimpleName(), f));
    }

    public record Builder() implements CommandMiddlewareBuilder {

        @Override
        public CommandMiddleware chain(CommandMiddleware next) {
            return new LoggingCommandBusMiddleware(next);
        }
    }

}
