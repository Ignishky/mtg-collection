package fr.ignishky.mtgcollection.framework.cqrs.query;

import java.lang.reflect.ParameterizedType;

public interface QueryHandler<Q extends Query<R>, R> {

    R handle(Q query);

    default Class<Q> listenTo() {
        return (Class<Q>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

}
