package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.set.Set;

public record SetRest(
    String code,
    String name,
    String icon
) {

    public static SetRest fromSet(Set set) {
        return new SetRest(set.code().value(), set.name().value(), set.icon().url());
    }

}
