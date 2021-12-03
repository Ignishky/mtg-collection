package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.api.rest.SetResponse;

import static fr.ignishky.mtgcollection.common.DomainFixtures.aSet;
import static fr.ignishky.mtgcollection.common.DomainFixtures.anotherSet;

public class ApiFixtures {

    public static SetResponse aRestSet = new SetResponse(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    public static SetResponse anotherRestSet = new SetResponse(anotherSet.code().value(), anotherSet.name().value(), anotherSet.icon().url());

}
