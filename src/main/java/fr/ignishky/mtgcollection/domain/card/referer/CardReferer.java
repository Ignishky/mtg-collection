package fr.ignishky.mtgcollection.domain.card.referer;

import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardFaces;
import io.vavr.collection.List;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface CardReferer {

    UUID id();

    String name();

    String set();

    boolean isDigital();

    CardImagesReferer images();

    List<CardFaces> cardFaces();

    PricesReferer prices();

    default boolean hasPrice() {
        return isNotBlank(prices().eur()) || isNotBlank(prices().eurFoil());
    }

    default boolean isNotDigital() {
        return !isDigital();
    }

    default boolean hasImage() {
        return images() != null || !cardFaces().isEmpty();
    }

}
