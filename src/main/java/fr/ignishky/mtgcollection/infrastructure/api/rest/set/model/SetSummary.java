package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record SetSummary(
        @Schema(description = "The official code of the set, always 3 chars.", example = "mir")
        String code,
        @Schema(description = "The full name of the set.", example = "Ice Age")
        String name,
        @Schema(description = "The scryfall URL for the icon of the set.",
                example = "https://c2.scryfall.com/file/scryfall-symbols/sets/ice.svg?1644814800")
        String icon,
        @Schema(description = "The number of cards int the set.")
        Number nbCards,
        @Schema(description = "The number of cards owned.")
        Number nbOwned,
        @Schema(description = "The number of foil cards owned.")
        Number nbFullyOwned
) {

}
