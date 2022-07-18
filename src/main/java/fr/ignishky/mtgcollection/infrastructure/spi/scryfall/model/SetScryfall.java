package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import fr.ignishky.mtgcollection.domain.set.*;
import io.vavr.collection.List;
import io.vavr.control.Option;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;

public record SetScryfall(List<ScryfallData> data) {

    public record ScryfallData(
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

        public Set toSet() {
            return new Set(toSetId(id),
                    new SetCode(code),
                    new SetName(name),
                    digital,
                    Option.of(parent_set_code).map(SetCode::new),
                    Option.of(block_code).map(SetCode::new),
                    released_at,
                    SetType.valueOf(set_type),
                    card_count,
                    new SetIcon(icon_svg_uri));
        }

    }

}
