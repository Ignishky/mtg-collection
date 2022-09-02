package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.set.*;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static fr.ignishky.mtgcollection.domain.set.SetType.*;
import static java.util.UUID.randomUUID;

public class SetFixtures {

    public static final Set StreetOfNewCapenna = new SetGenerator()
            .withSetCode(new SetCode("snc"))
            .withSetName(new SetName("Streets of New Capenna"))
            .withReleaseDate("2022-04-29")
            .withSetType(expansion)
            .withCardCount(467)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/snc.svg"))
            .generate();

    public static final Set JudgeGiftCards2022 = new SetGenerator()
            .withSetCode(new SetCode("p22"))
            .withSetName(new SetName("Judge Gift Cards 2022\""))
            .withBlockCode(new SetCode("jgp"))
            .withReleaseDate("2022-01-01")
            .withSetType(promo)
            .withCardCount(8)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/archie.svg"))
            .generate();

    public static final Set Kaldheim = new SetGenerator()
            .withSetCode(new SetCode("khm"))
            .withSetName(new SetName("Kaldheim"))
            .withReleaseDate("2020-04-24")
            .withSetType(expansion)
            .withCardCount(390)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/khm.svg"))
            .generate();

    public static final Set KaldheimArtSeries = new SetGenerator()
            .withSetCode(new SetCode("akhm"))
            .withSetName(new SetName("Kaldheim Art Series"))
            .withParentSetCode(Kaldheim.code())
            .withReleaseDate("2020-04-24")
            .withSetType(memorabilia)
            .withCardCount(15)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/khm.svg"))
            .generate();

    public static final Set KaldheimToken = new SetGenerator()
            .withSetCode(new SetCode("tkhm"))
            .withSetName(new SetName("Kaldheim Tokens"))
            .withParentSetCode(Kaldheim.code())
            .withReleaseDate("2020-04-24")
            .withSetType(token)
            .withCardCount(15)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/khm.svg"))
            .generate();

    public static final Set KaldheimPromo = new SetGenerator()
            .withSetCode(new SetCode("pkhm"))
            .withSetName(new SetName("Kaldheim Promos"))
            .withParentSetCode(Kaldheim.code())
            .withReleaseDate("2020-04-24")
            .withSetType(promo)
            .withCardCount(136)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/khm.svg"))
            .generate();

    public static final Set DuelDecks1 = new SetGenerator()
            .withSetCode(new SetCode("ddu"))
            .withSetName(new SetName("Duel Decks: Elves vs. Inventors"))
            .withReleaseDate("2018-04-06")
            .withSetType(duel_deck)
            .withCardCount(76)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/ddu.svg"))
            .generate();

    public static final Set aFailedSet = new SetGenerator()
            .withSetCode(new SetCode("fail"))
            .withSetName(new SetName("FAILED SET"))
            .withReleaseDate("2021-12-01")
            .withSetType(expansion)
            .withCardCount(1)
            .withSetIcon(new SetIcon("icon5"))
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

        public Set generate() {
            return new Set(setId, setCode, setName, isDigital, Option.of(parentSetCode), Option.of(blockCode), releaseDate, setType, cardCount, setIcon);
        }

    }

}
