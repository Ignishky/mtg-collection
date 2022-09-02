package fr.ignishky.mtgcollection.framework.cqrs.query.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class QueryDispatcherMiddleware extends QueryMiddleware {

    private final Map<Class<? extends Query>, QueryHandler> handlers;

    private QueryDispatcherMiddleware(QueryMiddleware next, List<QueryHandler<?, ?>> handlers) {
        super(next);
        this.handlers = handlers.toMap(handler -> Tuple.of(handler.listenTo(), handler));
    }

    @Override
    public <Q extends Query<R>, R> R handle(Q query) {
        return handlers
                .get(query.getClass())
                .map(handler -> (R) handler.handle(query))
                .getOrElseThrow(() -> new IllegalArgumentException("query handler not found for %s".formatted(query.getClass())));
    }

    public record Builder(
            java.util.Set<? extends QueryHandler<?, ?>> handlers
    ) implements QueryMiddlewareBuilder {

        @Override
        public QueryMiddleware chain(QueryMiddleware next) {
            return new QueryDispatcherMiddleware(next, List.ofAll(handlers));
        }

    }

}
