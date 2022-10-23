package fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.mapper;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.domain.collection.Collection;
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
                aCard.isOwned(),
                aCard.isOwnedFoil()
        );
    }

}
