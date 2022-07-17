package fr.ignishky.mtgcollection.framework.cqrs.command.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.control.Try;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class LoggingCommandBusMiddleware extends CommandMiddleware {

    private static final Logger LOGGER = getLogger(LoggingCommandBusMiddleware.class);

    private LoggingCommandBusMiddleware(CommandMiddleware next) {
        super(next);
    }

    @Override
    public <T> Try<CommandResponse<T>> handle(Command<T> message) {
        LOGGER.info("Executing {} with parameter {}", message.getClass().getSimpleName(), message);
        return next(message)
                .onSuccess(success -> LOGGER.info("Success on {}. Result : {}, Events : {}",
                        message.getClass().getSimpleName(),
                        success.value(),
                        success.events()))
                .onFailure(fail -> LOGGER.error("Error on {}", message.getClass().getSimpleName(), fail));
    }

    public record Builder() implements CommandMiddlewareBuilder {

        @Override
        public CommandMiddleware chain(CommandMiddleware next) {
            return new LoggingCommandBusMiddleware(next);
        }
    }

}
