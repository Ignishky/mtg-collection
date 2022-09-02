package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record BlocksResponse(
        @Schema(description = "The list of known cards blocks.")
        List<BlockSummary> blocks
) {

}
