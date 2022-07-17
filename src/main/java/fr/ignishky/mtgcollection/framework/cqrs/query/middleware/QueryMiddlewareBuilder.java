package fr.ignishky.mtgcollection.framework.cqrs.query.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public interface QueryMiddlewareBuilder {

    static QueryMiddleware build(Set<? extends QueryMiddlewareBuilder> builders) {
        return List.ofAll(builders).foldRight(new CircuitBreakerMiddleware(), QueryMiddlewareBuilder::chain);
    }

    QueryMiddleware chain(QueryMiddleware next);

    class CircuitBreakerMiddleware extends QueryMiddleware {

        CircuitBreakerMiddleware() {
            super(null);
        }

        @Override
        public <Q extends Query<R>, R> R handle(Q query) {
            throw new IllegalStateException("No final middleware provided in the chain");
        }
    }
}
