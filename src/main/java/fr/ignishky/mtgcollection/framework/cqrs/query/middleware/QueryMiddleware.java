package fr.ignishky.mtgcollection.framework.cqrs.query.middleware;

import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public abstract class QueryMiddleware {

    private final QueryMiddleware next;

    QueryMiddleware(QueryMiddleware next) {
        this.next = next;
    }

    public abstract <Q extends Query<R>, R> R handle(Q query);

    protected <R> R next(Query<R> query) {
        return next.handle(query);
    }

}
