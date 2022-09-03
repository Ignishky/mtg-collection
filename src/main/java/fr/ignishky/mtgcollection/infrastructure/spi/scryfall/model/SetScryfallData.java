package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

public record SetScryfallData(
        String id,
        String code,
        String name,
        boolean digital,
        String parent_set_code,
        String block_code,
        String released_at,
        String set_type,
        int card_count,
        String icon_svg_uri
) {

}
