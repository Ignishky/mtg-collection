package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record BlocksResponse(
        @Schema(description = "The number of cards in the block.")
        int nbCards,
        @Schema(description = "The number of owned card.")
        int nbOwned,
        @Schema(description = "The number of owned foil card.")
        int nbOwnedFoil,
        @Schema(description = "The list of known cards blocks.")
        List<BlockSummary> blocks
) {

}
