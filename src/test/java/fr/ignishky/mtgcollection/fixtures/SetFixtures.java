package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.set.*;
import io.vavr.control.Option;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;
import static fr.ignishky.mtgcollection.domain.set.SetType.*;
import static java.util.UUID.randomUUID;

public class SetFixtures {

    public static final Set SNC = new Set(
            toSetId("df837242-8c15-42e4-b049-c933a02dc501"),
            new SetCode("snc"),
            new SetName("Streets of New Capenna"),
            false,
            Option.none(),
            Option.none(),
            "2022-04-29",
            EXPANSION,
            467,
            0,
            new SetIcon("https://scryfall.mtgc.test/sets/snc.svg")
    );

    public static final Set P22 = new Set(
            new SetId(randomUUID()),
            new SetCode("p22"),
            new SetName("Judge Gift Cards 2022"),
            false,
            Option.none(),
            Option.of(new SetCode("jgp")),
            "2022-01-01",
            PROMO,
            8,
            0,
            new SetIcon("https://scryfall.mtgc.test/sets/archie.svg")
    );

    public static final Set KHM = new Set(
            toSetId("43057fad-b1c1-437f-bc48-0045bce6d8c9"),
            new SetCode("khm"),
            new SetName("Kaldheim"),
            false,
            Option.none(),
            Option.none(),
            "2021-02-05",
            EXPANSION,
            427,
            0,
            new SetIcon("https://scryfall.mtgc.test/sets/khm.svg")
    );

    public static final Set AKHM = new Set(
            new SetId(randomUUID()),
            new SetCode("akhm"),
            new SetName("Kaldheim Art Series"),
            false,
            Option.of(KHM.code()),
            Option.none(),
            "2020-04-24",
            MEMORABILIA,
            30,
            0,
            KHM.icon()
    );

    public static final Set TKHM = new Set(
            new SetId(randomUUID()),
            new SetCode("tkhm"),
            new SetName("Kaldheim Tokens"),
            false,
            Option.of(KHM.code()),
            Option.none(),
            "2020-04-24",
            TOKEN,
            15,
            0,
            KHM.icon()
    );

    public static final Set PKHM = new Set(
            new SetId(randomUUID()),
            new SetCode("pkhm"),
            new SetName("Kaldheim Promos"),
            false,
            Option.of(KHM.code()),
            Option.none(),
            "2020-04-24",
            PROMO,
            136,
            0,
            KHM.icon()
    );

    public static final Set DDU = new Set(
            new SetId(randomUUID()),
            new SetCode("ddu"),
            new SetName("Duel Decks: Elves vs. Inventors"),
            false,
            Option.none(),
            Option.none(),
            "2018-04-06",
            DUEL_DECK,
            76,
            0,
            new SetIcon("https://scryfall.mtgc.test/sets/ddu.svg")
    );

}
