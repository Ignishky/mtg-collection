package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import io.vavr.collection.List;

public record CardScryfall(
        String next_page,
        List<CardScryfallData> data
) {

}
