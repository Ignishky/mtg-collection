package fr.ignishky.mtgcollection.domain.set.query;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import io.vavr.collection.List;

public record GetSetResponse(
        String setName,
        Number nbOwned,
        Number nbFullyOwned,
        double fullValue,
        double ownedValue,
        List<Card> cards
) {

}
