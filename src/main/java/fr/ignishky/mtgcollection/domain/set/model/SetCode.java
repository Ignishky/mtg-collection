package fr.ignishky.mtgcollection.domain.set.model;

public record SetCode(
        String value
) {

    @Override
    public String toString() {
        return value;
    }

}
