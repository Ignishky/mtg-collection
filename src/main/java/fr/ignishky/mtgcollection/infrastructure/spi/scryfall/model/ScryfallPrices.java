package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.card.port.referer.model.CardRefererPrices;

import static java.lang.Double.parseDouble;

public record ScryfallPrices(
        String eur,
        @JsonProperty("eur_foil")
        String eurFoil
) implements CardRefererPrices {

    public ScryfallPrices(Double eur, Double eurFoil) {
        this(String.valueOf(eur), String.valueOf(eurFoil));
    }

    public Double euro() {
        return eur != null ? parseDouble(eur) : null;
    }

    public Double euroFoil() {
        return eurFoil != null ? parseDouble(eurFoil) : null;
    }

}
