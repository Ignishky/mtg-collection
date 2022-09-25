package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PriceResponse(
        Double eur,
        @JsonProperty("eur_foil")
        Double eurFoil
) {

}
