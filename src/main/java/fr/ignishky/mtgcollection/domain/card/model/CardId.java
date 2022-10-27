package fr.ignishky.mtgcollection.domain.card.model;

import java.util.UUID;

public record CardId(
        UUID id
) {

    public static CardId fromString(String name) {
        return new CardId(UUID.fromString(name));
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
