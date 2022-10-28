package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record SetResponse(
        @Schema(description = "The name of the set.")
        String name,
        @Schema(description = "The number of cards in the set.")
        Number nbCards,
        @Schema(description = "The number of cards owned in the set.")
        Number nbOwned,
        @Schema(description = "The number of foil cards owned in the set.")
        Number nbFullyOwned,
        @Schema(description = "The sum of all the cards in the set. In foil version if exists, normal otherwise.")
        double fullValue,
        @Schema(description = "The sum of all the cards in the collection.")
        double ownedValue,
        @Schema(description = "The list of the cards for the given set.")
        List<CardResponse> cards
) {

}
