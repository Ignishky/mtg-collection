package fr.ignishky.mtgcollection.domain.card;

import java.time.LocalDate;

public record Price(
        LocalDate date,
        String eur,
        String eurFoil
) {

}
