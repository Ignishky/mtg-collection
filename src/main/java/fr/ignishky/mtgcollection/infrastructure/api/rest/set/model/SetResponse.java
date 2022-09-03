package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record SetResponse(
        @Schema(description = "The name of the set.")
        String name,
        @Schema(description = "The list of the cards for the given set.")
        List<CardSummary> cards
) {

}
