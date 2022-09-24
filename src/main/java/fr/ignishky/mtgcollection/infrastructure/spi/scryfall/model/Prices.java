package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.card.referer.PricesReferer;

public record Prices(
        String eur,
        @JsonProperty("eur_foil")
        String eurFoil
) implements PricesReferer {

}
