package fr.ignishky.mtgcollection.domain.set;

public record SetCode(
        String value
) {

    @Override
    public String toString() {
        return value;
    }

}
