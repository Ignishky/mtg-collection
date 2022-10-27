package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.card.referer.model.CardReferer;
import fr.ignishky.mtgcollection.domain.card.referer.model.CardRefererImage;
import io.vavr.collection.List;

import java.util.UUID;

public record ScryfallCardData(
        UUID id,
        String name,
        String set,
        @JsonProperty("digital")
        boolean isDigital,
        @JsonProperty("image_uris")
        ScryfallCardImages images,
        @JsonProperty("card_faces")
        List<CardFaces> cardFaces,
        List<String> finishes,
        ScryfallPrices prices
) implements CardReferer {

    @Override
    public CardRefererImage image() {
        return images != null
                ? images
                : cardFaces.nonEmpty() && cardFaces.head().imageUris() != null
                ? cardFaces.head().imageUris()
                : null;
    }

}
