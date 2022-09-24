package fr.ignishky.mtgcollection.infrastructure.api.rest.block.model;

import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record SetsResponse(
        @Schema(description = "The name of the selected block.")
        String blockName,

        @Schema(description = "The list of known cards sets.")
        List<SetSummary> sets
) {

}
