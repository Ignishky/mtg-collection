package fr.ignishky.mtgcollection.domain.collection.model;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import io.vavr.collection.List;

public record Collection(
        Number nbOwned,
        Number nbFullyOwned,
        double fullValue,
        double ownedValue,
        List<Card> cards
) {

}
