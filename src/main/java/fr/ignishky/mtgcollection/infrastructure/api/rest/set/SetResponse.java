package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import io.swagger.v3.oas.annotations.media.Schema;

public record SetResponse(
        @Schema(description = "The official code of the set, always 3 chars.", example = "mir")
        String code,
        @Schema(description = "The full name of the set.", example = "Mirage")
        String name,
        @Schema(description = "The scryfall URL for the icon of the set.",
                example = "https://c2.scryfall.com/file/scryfall-symbols/sets/mir.svg?1644814800")
        String icon
) {

    public static SetResponse toSetResponse(Set aSet) {
        return new SetResponse(aSet.code().value(), aSet.name().value(), aSet.icon().url());
    }

}
