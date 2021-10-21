package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall.ScryfallData;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.Blocks.*;
import static java.util.UUID.randomUUID;

public class ScryfallBlocks {

    public static BlockScryfall aScryfallBlocks() {
        return new BlockScryfall(List.of(
                new ScryfallData(randomUUID().toString(), "wtf", "NON-EXISTING SETS", "9999-12-31", null,null, null, null),
                new ScryfallData(GAME_NIGHT.id().uuid().toString(), GAME_NIGHT.code(), GAME_NIGHT.name(), "2018-11-16", "box", null, null, null),
                new ScryfallData(SCOURGE.id().uuid().toString(), SCOURGE.code(), SCOURGE.name(), "2003-05-26", "promo",null, "ons", null),
                new ScryfallData(INNISTRAD_MIDNIGHT_HUNT.id().uuid().toString(), INNISTRAD_MIDNIGHT_HUNT.code(), INNISTRAD_MIDNIGHT_HUNT.name(), "2021-09-24", "expansion", null, null, "innistrad_icon"),
                new ScryfallData(ADVENTURE_OF_THE_FORGOTTEN_REALMS.id().uuid().toString(), ADVENTURE_OF_THE_FORGOTTEN_REALMS.code(), ADVENTURE_OF_THE_FORGOTTEN_REALMS.name(), "2021-07-23", "expansion", null, null, "realms_icon"),
                new ScryfallData(CORE_SET_2020_TOKENS.id().uuid().toString(), CORE_SET_2020_TOKENS.code(), CORE_SET_2020_TOKENS.name(), "2019-07-12", "token", "m20", null, null)));
    }
}
