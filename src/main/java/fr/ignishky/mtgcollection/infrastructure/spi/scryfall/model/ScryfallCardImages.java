package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import fr.ignishky.mtgcollection.domain.card.referer.model.CardRefererImage;

public record ScryfallCardImages(
        String normal
) implements CardRefererImage {

}
