package fr.ignishky.mtgcollection.framework.cqrs.query;

import fr.ignishky.mtgcollection.framework.cqrs.query.middleware.QueryMiddleware;
import fr.ignishky.mtgcollection.framework.cqrs.query.middleware.QueryMiddlewareBuilder;
import io.vavr.collection.Set;

public class DirectQueryBus implements QueryBus {

    private final QueryMiddleware middlewareChain;

    public DirectQueryBus(Set<QueryMiddlewareBuilder> builders) {
        this.middlewareChain = QueryMiddlewareBuilder.build(builders);
    }

    @Override
    public <R> R dispatch(Query<R> query) {
        return middlewareChain.handle(query);
    }
}
