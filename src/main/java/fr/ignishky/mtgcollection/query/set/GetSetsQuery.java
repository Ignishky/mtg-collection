package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public record GetSetsQuery(
        SetCode setCode
) implements Query<GetSetsResponse> {

}
