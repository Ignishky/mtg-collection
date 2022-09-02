package fr.ignishky.mtgcollection.infrastructure.api.rest.set.model;

import fr.ignishky.mtgcollection.domain.set.Set;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.List;

public record SetsResponse(
        @Schema(description = "The list of known cards sets.")
        List<SetSummary> sets
) {

    public record SetSummary(
            @Schema(description = "The official code of the set, always 3 chars.", example = "mir")
            String code,
            @Schema(description = "The full name of the set.", example = "Ice Age")
            String name,
            @Schema(description = "The scryfall URL for the icon of the set.",
                    example = "https://c2.scryfall.com/file/scryfall-symbols/sets/ice.svg?1644814800")
            String icon
    ) {

        public static SetSummary toSetSummary(Set aSet) {
            return new SetSummary(aSet.code().value(), aSet.name().value(), aSet.icon().url());
        }

    }

}
