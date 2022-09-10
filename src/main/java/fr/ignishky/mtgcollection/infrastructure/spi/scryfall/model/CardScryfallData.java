package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

import java.util.UUID;

public record CardScryfallData(
        UUID id,
        String name,
        String set,
        boolean digital,
        @JsonProperty("image_uris")
        CardImages imageUris,
        @JsonProperty("card_faces")
        List<CardFaces> cardFaces,
        Prices prices
) {

    public boolean isNotDigital() {
        return !digital;
    }

    public boolean hasImage() {
        return imageUris != null || !cardFaces.isEmpty();
    }

}
