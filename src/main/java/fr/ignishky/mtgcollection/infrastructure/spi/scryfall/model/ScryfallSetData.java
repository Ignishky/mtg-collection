package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ignishky.mtgcollection.domain.set.referer.SetReferer;

public record ScryfallSetData(
        String id,
        String code,
        String name,
        boolean digital,
        @JsonProperty("parent_set_code")
        String parentSetCode,
        @JsonProperty("block_code")
        String blockCode,
        @JsonProperty("released_at")
        String releasedAt,
        @JsonProperty("set_type")
        String setType,
        @JsonProperty("card_count")
        int cardCount,
        @JsonProperty("icon_svg_uri")
        String icon
) implements SetReferer {

}
