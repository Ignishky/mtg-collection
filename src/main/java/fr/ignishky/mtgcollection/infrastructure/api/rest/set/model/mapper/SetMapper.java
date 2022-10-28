package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.mapper;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.Finish;
import fr.ignishky.mtgcollection.domain.card.model.Price;
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
                set.nbFullyOwned(),
                parseDouble(String.format("%.2f", set.fullValue())),
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
                aCard.finishes().map(Finish::name),
                aCard.ownState()
        );
    }

}
