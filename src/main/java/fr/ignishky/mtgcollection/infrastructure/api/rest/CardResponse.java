package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.card.Card;

import java.util.UUID;

public record CardResponse(
        UUID id,
        String name,
        String image
) {

    public static CardResponse toCardResponse(Card aCard) {
        return new CardResponse(aCard.id().id(), aCard.cardName().name(), aCard.cardImage().image());
    }

}
