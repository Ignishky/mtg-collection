package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.set.Set;

public record SetResponse(
    String code,
    String name,
    String icon
) {

    public static SetResponse toSetResponse(Set aSet) {
        return new SetResponse(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    }

}
