package fr.ignishky.mtgcollection.domain.card.port.referer.model;

import io.vavr.collection.List;

import java.util.UUID;

public interface CardReferer {

    UUID id();

    String name();

    String set();

    boolean isDigital();

    CardRefererImage image();

    List<String> finishes();

    CardRefererPrices prices();

    default boolean hasPrice() {
        var euro = prices().euro();
        var euroFoil = prices().euroFoil();
        return (euro != null && euro > 0.0)
                || (euroFoil != null && euroFoil > 0.0);
    }

    default boolean isNotDigital() {
        return !isDigital();
    }

    default boolean hasImage() {
        return image() != null;
    }

}
