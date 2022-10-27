package fr.ignishky.mtgcollection.domain.set.port.referer;

import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;

public interface SetReferer {

    String id();

    String code();

    String name();

    boolean digital();

    String parentSetCode();

    String blockCode();

    String releasedAt();

    String setType();

    int cardCount();

    String icon();

    default boolean hasBeenReleased() {
        return parse(releasedAt()).isBefore(now());
    }

    default boolean hasCard() {
        return cardCount() > 0;
    }

    default boolean isNotDigital() {
        return !digital();
    }

}
