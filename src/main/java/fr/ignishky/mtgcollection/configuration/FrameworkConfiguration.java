package fr.ignishky.mtgcollection.configuration;

import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.DirectCommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.command.middleware.CommandDispatcherMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.command.middleware.EventPersistenceMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.command.middleware.LoggingCommandBusMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.event.EventStore;
import fr.ignishky.mtgcollection.framework.cqrs.query.DirectQueryBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.framework.cqrs.query.middleware.LoggingQueryBusMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.query.middleware.QueryDispatcherMiddleware;
import io.vavr.collection.LinkedHashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class FrameworkConfiguration {

    @Bean
    public CommandBus commandBus(
            EventStore eventStore,
            Set<CommandHandler<?, ?>> handlers
    ) {
        return new DirectCommandBus(LinkedHashSet.of(
                new LoggingCommandBusMiddleware.Builder(),
                new EventPersistenceMiddleware.Builder(eventStore),
                new CommandDispatcherMiddleware.Builder(handlers)
        ));
    }

    @Bean
    public QueryBus queryBus(Set<? extends QueryHandler<?, ?>> handlers) {
        return new DirectQueryBus(LinkedHashSet.of(
                new LoggingQueryBusMiddleware.Builder(),
                new QueryDispatcherMiddleware.Builder(handlers)
        ));
    }
}
