package fr.ignishky.mtgcollection.domain.block;

import fr.ignishky.mtgcollection.domain.set.Set;
import io.vavr.collection.List;

public record Block(
        BlockCode code,
        BlockName name,
        Number nbCards,
        Number nbOwned,
        Number nbFoilOwned,
        BlockIcon icon,
        List<Set> sets
) {

}
