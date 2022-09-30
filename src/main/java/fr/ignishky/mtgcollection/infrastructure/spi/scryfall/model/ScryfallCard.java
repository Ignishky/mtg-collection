package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

public record ScryfallCard(
        @JsonProperty("next_page")
        String nextPage,
        List<ScryfallCardData> data
) {

}
