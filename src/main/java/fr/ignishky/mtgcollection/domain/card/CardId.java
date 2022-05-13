package fr.ignishky.mtgcollection.domain.card;

import java.util.UUID;

public record CardId(
        UUID id
) {

    public static CardId fromString(String uuidAsString) {
        return new CardId(UUID.fromString(uuidAsString));
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
