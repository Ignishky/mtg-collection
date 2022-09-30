package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import io.vavr.collection.List;

public record ScryfallSet(
        List<ScryfallSetData> data
) {

}
