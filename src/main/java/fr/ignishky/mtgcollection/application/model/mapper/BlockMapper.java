package fr.ignishky.mtgcollection.application.model.mapper;

import fr.ignishky.mtgcollection.application.model.BlockResponse;
import fr.ignishky.mtgcollection.domain.block.model.Block;

public class BlockMapper {
    public static BlockResponse toBlockResponse(Block block) {
        return new BlockResponse(block.code(), block.name(), block.icon());
    }
}
