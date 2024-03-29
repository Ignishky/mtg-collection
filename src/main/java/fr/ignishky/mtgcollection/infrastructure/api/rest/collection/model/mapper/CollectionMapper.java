package fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.mapper;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.Finish;
import fr.ignishky.mtgcollection.domain.card.model.Price;
import fr.ignishky.mtgcollection.domain.collection.model.Collection;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.PriceResponse;

public class CollectionMapper {

    public static CollectionResponse toCollection(Collection collection) {
        return new CollectionResponse(collection
                .cards()
                .map(CollectionMapper::toCardResponse)
        );
    }

    public static CardResponse toCardResponse(Card aCard) {
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
