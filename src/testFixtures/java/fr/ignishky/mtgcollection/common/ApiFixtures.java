package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.api.rest.SetRest;

import static fr.ignishky.mtgcollection.common.DomainFixtures.aSet;
import static fr.ignishky.mtgcollection.common.DomainFixtures.anotherSet;

public class ApiFixtures {

    public static SetRest aRestSet = new SetRest(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    public static SetRest anotherRestSet = new SetRest(anotherSet.code().value(), anotherSet.name().value(), anotherSet.icon().url());

}
