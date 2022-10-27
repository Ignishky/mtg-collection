package fr.ignishky.mtgcollection.domain.set.query;

import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public record GetSetQuery(
        SetCode code
) implements Query<GetSetResponse> {

}
