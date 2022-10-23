package fr.ignishky.mtgcollection.domain.card;

import io.vavr.collection.List;

public enum Finish {
    FOIL("foil"),
    NON_FOIL("nonfoil"),
    ETCHED("etched");

    private final String value;

    Finish(String value) {
        this.value = value;
    }

    public static Finish fromValue(String value) {
        return List.of(values())
                .filter(finish -> finish.value.equals(value))
                .head();
    }
}
