package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.card.referer.PricesReferer;

import static java.lang.Double.parseDouble;

public record Prices(
        String eur,
        @JsonProperty("eur_foil")
        String eurFoil
) implements PricesReferer {

    public Prices(Double eur, Double eurFoil) {
        this(String.valueOf(eur), String.valueOf(eurFoil));
    }

    public Double euro() {
        return parseDouble(eur);
    }

    public Double euroFoil() {
        return parseDouble(eurFoil);
    }

}
