package fr.ignishky.mtgcollection.framework.cqrs.query.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class LoggingQueryBusMiddleware extends QueryMiddleware {

    private static final Logger LOGGER = getLogger(LoggingQueryBusMiddleware.class);

    private LoggingQueryBusMiddleware(QueryMiddleware next) {
        super(next);
    }

    @Override
    public <Q extends Query<R>, R> R handle(Q query) {
        try {
            LOGGER.info("Executing {} with query {}", query.getClass().getSimpleName(), query);
            R result = next(query);
            LOGGER.info("Success on {} for query {}", query.getClass().getSimpleName(), query);

            return result;
        } catch (Exception e) {
            LOGGER.error("Error on {} for query {}", query.getClass().getSimpleName(), query, e);
            throw e;
        }
    }

    public record Builder() implements QueryMiddlewareBuilder {

        @Override
        public QueryMiddleware chain(QueryMiddleware next) {
            return new LoggingQueryBusMiddleware(next);
        }

    }

}
