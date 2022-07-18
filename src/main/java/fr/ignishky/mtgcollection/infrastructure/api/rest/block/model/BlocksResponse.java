package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import fr.ignishky.mtgcollection.domain.block.Block;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record BlocksResponse(
        @Schema(description = "The list of known cards blocks.")
        List<BlockSummary> blocks
) {

    public record BlockSummary(
            @Schema(description = "The official code of the block, always 3 chars.", example = "mir")
            String code,
            @Schema(description = "The full name of the block.", example = "Mirage")
            String name,
            @Schema(description = "The scryfall URL for the icon of the block.",
                    example = "https://c2.scryfall.com/file/scryfall-symbols/sets/mir.svg?1644814800")
            String icon
    ) {

        public static BlockSummary toBlockSummary(Block block) {
            return new BlockSummary(block.code().value(), block.name().value(), block.icon().url());
        }

    }

}
