package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.mapper;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.domain.set.query.GetSetResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.PriceResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse;

import static java.lang.Double.parseDouble;

public class SetMapper {

    public static SetResponse toSetResponse(GetSetResponse set) {
        return new SetResponse(
                set.setName(),
                set.cards().size(),
                set.nbOwned(),
                set.nbFoilOwned(),
                parseDouble(String.format("%.2f", set.maxValue())),
                parseDouble(String.format("%.2f", set.ownedValue())),
                set.cards().map(SetMapper::toCardResponse)
        );
    }

    private static CardResponse toCardResponse(Card aCard) {
        Price price = aCard.prices();
        return new CardResponse(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.cardImage().image(),
                new PriceResponse(price.eur(), price.eurFoil()),
                aCard.isOwned(),
                aCard.isOwnedFoil()
        );
    }

}
