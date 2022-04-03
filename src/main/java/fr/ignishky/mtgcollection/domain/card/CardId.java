package fr.ignishky.mtgcollection.domain.card;

import java.util.UUID;

public record CardId(
        UUID id
) {

    @Override
    public String toString() {
        return id.toString();
    }

}
