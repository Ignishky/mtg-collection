package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

public record CardScryfall(
        @JsonProperty("next_page")
        String nextPage,
        List<CardScryfallData> data
) {

}
