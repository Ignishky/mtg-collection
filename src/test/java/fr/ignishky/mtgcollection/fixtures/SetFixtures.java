package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.set.*;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static java.util.UUID.randomUUID;

public class SetFixtures {

    public static final Set StreetOfNewCapenna = new SetGenerator()
            .withSetCode(new SetCode("snc"))
            .withSetName(new SetName("Streets of New Capenna"))
            .withReleaseDate("2022-04-29")
            .withCardCount(467)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/snc.svg"))
            .generate();

    public static final Set Ikoria = new SetGenerator()
            .withSetCode(new SetCode("iko"))
            .withSetName(new SetName("Ikoria: Lair of Behemoths"))
            .withReleaseDate("2020-04-24")
            .withCardCount(390)
            .withSetIcon(new SetIcon("https://scryfall.mtgc.test/sets/iko.svg"))
            .generate();

    public static final Set aFailedSet = new SetGenerator()
            .withSetCode(new SetCode("fail"))
            .withSetName(new SetName("FAILED SET"))
            .withReleaseDate("2021-12-01")
            .withCardCount(1)
            .withSetIcon(new SetIcon("icon5"))
            .generate();

    @With
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetGenerator {

        private SetId setId = new SetId(randomUUID());
        private SetCode setCode;
        private SetName setName;
        private boolean isDigital;
        private Option<SetCode> parentSetCode = Option.none();
        private Option<SetCode> blockCode = Option.none();
        private String releaseDate;
        private int cardCount;
        private SetIcon setIcon;

        public Set generate() {
            return new Set(setId, setCode, setName, isDigital, parentSetCode, blockCode, releaseDate, cardCount, setIcon);
        }

    }

}
