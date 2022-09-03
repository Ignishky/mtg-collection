package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

import java.util.UUID;

public record CardScryfallData(
        UUID id,
        String name,
        String set,
        @JsonProperty("image_uris")
        CardImages imageUris,
        @JsonProperty("card_faces")
        List<CardFaces> cardFaces
) {

}
