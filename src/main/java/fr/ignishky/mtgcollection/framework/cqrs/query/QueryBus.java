package fr.ignishky.mtgcollection.framework.cqrs.query;

public interface QueryBus {

    <R> R dispatch(Query<R> query);

}
