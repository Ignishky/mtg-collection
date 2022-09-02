package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import io.vavr.collection.List;

public record GetSetsQuery(
        SetCode setCode
) implements Query<List<Set>> {

}
