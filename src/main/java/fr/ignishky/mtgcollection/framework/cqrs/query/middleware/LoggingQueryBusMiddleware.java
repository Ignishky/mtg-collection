package fr.ignishky.mtgcollection.framework.cqrs.query.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingQueryBusMiddleware extends QueryMiddleware {

    private LoggingQueryBusMiddleware(QueryMiddleware next) {
        super(next);
    }

    @Override
    public <Q extends Query<R>, R> R handle(Q query) {
        try {
            log.info("Executing {} with query {}", query.getClass().getSimpleName(), query);
            R result = next(query);
            log.info("Success on {} for query {}", query.getClass().getSimpleName(), query);

            return result;
        } catch (Exception e) {
            log.error("Error on {} for query {}", query.getClass().getSimpleName(), query);
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
