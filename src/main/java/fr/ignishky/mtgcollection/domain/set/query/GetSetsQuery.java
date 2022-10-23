package fr.ignishky.mtgcollection.domain.set.query;

import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public record GetSetsQuery(
        SetCode setCode
) implements Query<GetSetsResponse> {

}
