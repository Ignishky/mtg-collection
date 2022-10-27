package fr.ignishky.mtgcollection.domain.card.model;

import static java.lang.Double.parseDouble;

public record Price(
        Double eur,
        Double eurFoil
) {

    public Price(String eur, String eurFoil) {
        this(
                eur != null ? parseDouble(eur) : null,
                eurFoil != null ? parseDouble(eurFoil) : null
        );
    }

}
