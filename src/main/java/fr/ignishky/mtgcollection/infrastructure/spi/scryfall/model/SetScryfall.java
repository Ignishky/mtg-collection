package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetIcon;
import fr.ignishky.mtgcollection.domain.set.SetName;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;

public record SetScryfall(List<ScryfallData> data) {
    public record ScryfallData(
            String id,
            String code,
            String name,
            String released_at,
            String icon_svg_uri
    ) {

        public Set toSet() {
            return new Set(toSetId(id), new SetCode(code), new SetName(name), released_at, new SetIcon(icon_svg_uri));
        }

    }
}
