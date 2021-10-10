package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall.ScryfallData;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.Blocks.*;

public class ScryfallBlocks {

    public static BlockScryfall aScryfallBlocks() {
        return new BlockScryfall(List.of(
                new ScryfallData(SCOURGE.id().uuid().toString(), SCOURGE.code(), SCOURGE.name(), null),
                new ScryfallData(CORE_SET_2020.id().uuid().toString(), CORE_SET_2020.code(), CORE_SET_2020.name(), null),
                new ScryfallData(CORE_SET_2020_TOKENS.id().uuid().toString(), CORE_SET_2020_TOKENS.code(), CORE_SET_2020_TOKENS.name(), CORE_SET_2020.code())));
    }
}
