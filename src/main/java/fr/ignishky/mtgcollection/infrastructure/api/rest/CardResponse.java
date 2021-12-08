package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.card.Card;

import java.util.UUID;

public record CardResponse(
        UUID id,
        String name,
        String image
) {
    public static CardResponse fromCard(Card card) {
        return new CardResponse(card.id().id(), card.cardName().name(), card.cardImage().image());
    }
}
