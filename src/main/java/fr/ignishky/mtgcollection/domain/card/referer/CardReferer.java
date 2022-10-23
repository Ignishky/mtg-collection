package fr.ignishky.mtgcollection.domain.card.referer;

import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardFaces;
import io.vavr.collection.List;

import java.util.UUID;

public interface CardReferer {

    UUID id();

    String name();

    String set();

    boolean isDigital();

    CardImagesReferer images();

    List<CardFaces> cardFaces();

    List<String> finishes();

    PricesReferer prices();

    default boolean hasPrice() {
        Double euro = prices().euro();
        Double euroFoil = prices().euroFoil();
        return (euro != null && euro > 0.0)
                || (euroFoil != null && euroFoil > 0.0);
    }

    default boolean isNotDigital() {
        return !isDigital();
    }

    default boolean hasImage() {
        return images() != null
                || (cardFaces().nonEmpty() && cardFaces().head().imageUris() != null);
    }

}
