package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.mapper;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlockResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlockSummary;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetSummary;
import io.vavr.collection.List;

public class BlockMapper {

    public static BlocksResponse toBlocksResponse(List<Block> blocks) {
        List<BlockSummary> blockSummaries = blocks.map(BlockMapper::toBlockSummary);
        return new BlocksResponse(
                blockSummaries.map(BlockSummary::nbCards).sum(),
                blockSummaries.map(BlockSummary::nbOwned).sum(),
                blockSummaries.map(BlockSummary::nbFoilOwned).sum(),
                blockSummaries
        );
    }

    private static BlockSummary toBlockSummary(Block block) {
        return new BlockSummary(
                block.code().value(),
                block.name().value(),
                block.nbCards(),
                block.nbOwned(),
                block.nbFoilOwned(),
                block.icon().url()
        );
    }

    public static BlockResponse toBlockResponse(Block block) {
        return new BlockResponse(
                block.name().value(),
                block.nbCards(),
                block.nbOwned(),
                block.nbFoilOwned(),
                block.sets().map(BlockMapper::toSetSummary)
        );
    }

    private static SetSummary toSetSummary(Set aSet) {
        return new SetSummary(
                aSet.code().value(),
                aSet.name().value(),
                aSet.icon().url(),
                aSet.cardCount(),
                aSet.cardOwnedCount(),
                aSet.cardFoilOwnedCount()
        );
    }

}
