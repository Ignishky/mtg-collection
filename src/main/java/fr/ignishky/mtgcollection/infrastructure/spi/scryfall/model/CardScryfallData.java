package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import io.vavr.collection.List;

import java.util.UUID;

public record CardScryfallData(
        UUID id,
        String name,
        String set,
        CardImages image_uris,
        List<CardFaces> card_faces
) {

}
