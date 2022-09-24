package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetName;
import io.vavr.collection.List;

public record GetSetsResponse(
        SetName blockName,
        List<Set> sets
) {

}
