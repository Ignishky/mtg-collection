package fr.ignishky.mtgcollection.domain.collection.query;

import fr.ignishky.mtgcollection.domain.collection.Collection;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public record GetCollectionQuery(
) implements Query<Collection> {

}
