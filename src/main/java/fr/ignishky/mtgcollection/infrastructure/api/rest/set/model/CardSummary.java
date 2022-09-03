package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CardSummary(
        @Schema(description = "The Scryfall id of the card.")
        UUID id,
        @Schema(description = "The official name of the card.", example = "Afterlife")
        String name,
        @Schema(description = "The Scryfall URL of the card image.",
                example = "https://c1.scryfall.com/file/scryfall-cards/normal/front/4/6/4644694d-52e6-4d00-8cad-748899eeea84.jpg?1562718804")
        String image,
        @Schema(description = "Is the card in your collection ?")
        boolean isOwned,
        @Schema(description = "If the card is in your collection is it foiled ?")
        boolean isFoiled
) {

}
