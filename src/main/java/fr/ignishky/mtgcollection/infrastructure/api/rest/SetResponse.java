package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.set.Set;

public record SetResponse(
    String code,
    String name,
    String icon
) {

    public static SetResponse fromSet(Set set) {
        return new SetResponse(set.code().value(), set.name().value(), set.icon().url());
    }

}
