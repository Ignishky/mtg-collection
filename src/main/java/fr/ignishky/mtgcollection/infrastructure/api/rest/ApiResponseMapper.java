package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.block.Block;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlockSummary;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.CardSummary;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.PriceResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetSummary;

public class ApiResponseMapper {

    private ApiResponseMapper() {
    }

    public static BlockSummary toBlockSummary(Block block) {
        return new BlockSummary(block.code().value(), block.name().value(), block.icon().url());
    }

    public static SetSummary toSetSummary(Set aSet) {
        return new SetSummary(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    }

    public static CardSummary toCardSummary(Card aCard) {
        Price price = aCard.prices();
        return new CardSummary(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.cardImage().image(),
                new PriceResponse(price.eur(), price.eurFoil()),
                aCard.isOwned(),
                aCard.isOwnedFoil());
    }

    public static CardResponse toCardResponse(Card aCard) {
        return new CardResponse(aCard.id().id(), aCard.cardName().name(), aCard.cardImage().image(), aCard.isOwned(), aCard.isOwnedFoil());
    }

}
