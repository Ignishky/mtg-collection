package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SetScryfallData(
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
        int card_count,
        @JsonProperty("icon_svg_uri")
        String iconSvgUri
) {

}
