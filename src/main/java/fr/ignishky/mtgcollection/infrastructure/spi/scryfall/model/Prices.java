package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Prices(
        String eur,
        @JsonProperty("eur_foil")
        String eurFoil
) {

}
