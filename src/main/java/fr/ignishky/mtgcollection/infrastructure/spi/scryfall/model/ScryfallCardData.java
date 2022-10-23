package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.card.referer.CardReferer;
import io.vavr.collection.List;

import java.util.UUID;

public record ScryfallCardData(
        UUID id,
        String name,
        String set,
        @JsonProperty("digital")
        boolean isDigital,
        @JsonProperty("image_uris")
        CardImages images,
        @JsonProperty("card_faces")
        List<CardFaces> cardFaces,
        List<String> finishes,
        Prices prices
) implements CardReferer {

}
