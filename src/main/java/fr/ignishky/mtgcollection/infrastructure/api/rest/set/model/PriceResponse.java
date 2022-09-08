package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PriceResponse(
        String eur,
        @JsonProperty("eur_foil")
        String eurFoil
) {

}
