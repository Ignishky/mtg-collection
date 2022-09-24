package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import fr.ignishky.mtgcollection.domain.card.referer.CardImagesReferer;

public record CardImages(
        String normal
) implements CardImagesReferer {

}
