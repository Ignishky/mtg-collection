package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record BlockSummary(
        @Schema(description = "The official code of the block, always 3 chars.", example = "mir")
        String code,
        @Schema(description = "The full name of the block.", example = "Mirage")
        String name,
        @Schema(description = "The scryfall URL for the icon of the block.",
                example = "https://c2.scryfall.com/file/scryfall-symbols/sets/mir.svg?1644814800")
        String icon
) {

}
