package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.api.rest.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.SetResponse;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;

public class ApiFixtures {

    public static SetResponse aRestSet = new SetResponse(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    public static SetResponse anotherRestSet = new SetResponse(anotherSet.code().value(), anotherSet.name().value(), anotherSet.icon().url());

    public static CardResponse aRestCard = new CardResponse(aCard.id().id(), aCard.cardName().name(), aCard.cardImage().image());
    public static CardResponse anExtraRestCard = new CardResponse(anExtraCard.id().id(), anExtraCard.cardName().name(), anExtraCard.cardImage().image());
}
