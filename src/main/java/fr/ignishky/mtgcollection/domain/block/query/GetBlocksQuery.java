package fr.ignishky.mtgcollection.domain.block.query;

import fr.ignishky.mtgcollection.domain.block.Block;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import io.vavr.collection.List;

public record GetBlocksQuery() implements Query<List<Block>> {

}
