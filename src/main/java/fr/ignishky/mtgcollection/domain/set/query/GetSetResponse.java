package fr.ignishky.mtgcollection.domain.set.query;

import fr.ignishky.mtgcollection.domain.card.Card;
import io.vavr.collection.List;

public record GetSetResponse(
        String setName,
        Number nbOwned,
        Number nbFoilOwned,
        double maxValue,
        double ownedValue,
        List<Card> cards
) {

}