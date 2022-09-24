package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.set.*;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static fr.ignishky.mtgcollection.domain.set.SetType.*;
import static java.util.UUID.randomUUID;

public class SetFixtures {

    private SetFixtures() {
    }

    public static final Set SNC = new SetGenerator()
            .withSetCode(new SetCode("snc"))
            .withSetName(new SetName("Streets of New Capenna"))
            .withReleaseDate("2022-04-29")
            .withSetType(EXPANSION)
            .withCardCount(467)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/snc.svg"))
            .generate();

    public static final Set P22 = new SetGenerator()
            .withSetCode(new SetCode("p22"))
            .withSetName(new SetName("Judge Gift Cards 2022\""))
            .withBlockCode(new SetCode("jgp"))
            .withReleaseDate("2022-01-01")
            .withSetType(PROMO)
            .withCardCount(8)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/archie.svg"))
            .generate();

    public static final Set KHM = new SetGenerator()
            .withSetCode(new SetCode("khm"))
            .withSetName(new SetName("Kaldheim"))
            .withReleaseDate("2020-04-24")
            .withSetType(EXPANSION)
            .withCardCount(390)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/khm.svg"))
            .generate();

    public static final Set AKHM = new SetGenerator()
            .withSetCode(new SetCode("akhm"))
            .withSetName(new SetName("Kaldheim Art Series"))
            .withParentSetCode(KHM.code())
            .withReleaseDate("2020-04-24")
            .withSetType(MEMORABILIA)
            .withCardCount(15)
            .withSetIcon(KHM.icon())
            .generate();

    public static final Set TKHM = new SetGenerator()
            .withSetCode(new SetCode("tkhm"))
            .withSetName(new SetName("Kaldheim Tokens"))
            .withParentSetCode(KHM.code())
            .withReleaseDate("2020-04-24")
            .withSetType(TOKEN)
            .withCardCount(15)
            .withSetIcon(KHM.icon())
            .generate();

    public static final Set PKHM = new SetGenerator()
            .withSetCode(new SetCode("pkhm"))
            .withSetName(new SetName("Kaldheim Promos"))
            .withParentSetCode(KHM.code())
            .withReleaseDate("2020-04-24")
            .withSetType(PROMO)
            .withCardCount(136)
            .withSetIcon(KHM.icon())
            .generate();

    public static final Set DDU = new SetGenerator()
            .withSetCode(new SetCode("ddu"))
            .withSetName(new SetName("Duel Decks: Elves vs. Inventors"))
            .withReleaseDate("2018-04-06")
            .withSetType(DUEL_DECK)
            .withCardCount(76)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/ddu.svg"))
            .generate();

    @With
    @NoArgsConstructor
    @AllArgsConstructor
    static class SetGenerator {

        private SetId setId = new SetId(randomUUID());
        private SetCode setCode;
        private SetName setName;
        private boolean isDigital;
        private SetCode parentSetCode;
        private SetCode blockCode;
        private String releaseDate;
        private SetType setType;
        private int cardCount;
        private SetIcon setIcon;

        Set generate() {
            return new Set(setId, setCode, setName, isDigital, Option.of(parentSetCode), Option.of(blockCode), releaseDate, setType, cardCount, setIcon);
        }

    }

}
