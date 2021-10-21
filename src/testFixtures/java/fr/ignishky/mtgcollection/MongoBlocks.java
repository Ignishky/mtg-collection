package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockMongo;

public class MongoBlocks {

    public static BlockMongo aMongoBlock(Block block) {
        return new BlockMongo(block.id().uuid().toString(), block.code(), block.name(), block.icon());
    }
}
