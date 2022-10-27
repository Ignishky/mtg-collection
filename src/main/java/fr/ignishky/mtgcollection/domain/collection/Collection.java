package fr.ignishky.mtgcollection.domain.collection;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import io.vavr.collection.List;

public record Collection(
        Number nbOwned,
        Number nbFoilOwned,
        double maxValue,
        double ownedValue,
        List<Card> cards
) {

}
