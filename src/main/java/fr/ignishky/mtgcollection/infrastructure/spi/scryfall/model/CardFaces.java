package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardFaces(
        @JsonProperty("image_uris")
        ScryfallCardImages imageUris
) {

}
