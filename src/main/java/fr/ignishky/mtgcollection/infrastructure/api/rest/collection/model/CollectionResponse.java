package fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model;

import io.vavr.collection.List;

public record CollectionResponse(
        List<CardResponse> cards
) {

}
