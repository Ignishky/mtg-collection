package fr.ignishky.mtgcollection.domain.set;

import java.util.UUID;

public record SetId(
        UUID id
) {

    public static SetId toSetId(String id) {
        return new SetId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
