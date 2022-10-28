package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record BlocksResponse(
        @Schema(description = "The number of cards in all MTG.")
        Number nbCards,
        @Schema(description = "The total number of cards owned.")
        Number nbOwned,
        @Schema(description = "The total number of foil cards owned.")
        Number nbFullyOwned,
        @Schema(description = "The list of all MTG blocks.")
        List<BlockSummary> blocks
) {

}
