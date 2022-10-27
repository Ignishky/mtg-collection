package fr.ignishky.mtgcollection.domain.block.query;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;

public record GetBlockQuery(
        SetCode setCode
) implements Query<Block> {

}
