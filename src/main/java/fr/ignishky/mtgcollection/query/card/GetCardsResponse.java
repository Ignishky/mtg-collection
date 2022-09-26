package fr.ignishky.mtgcollection.query.card;

import fr.ignishky.mtgcollection.domain.card.Card;
import io.vavr.collection.List;
import io.vavr.control.Option;

public record GetCardsResponse(
        Option<String> setName,
        int nbOwned,
        int nbOwnedFoil,
        double maxValue,
        double ownedValue,
        List<Card> cards
) {

}
