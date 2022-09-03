package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetIcon;
import fr.ignishky.mtgcollection.domain.set.SetName;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfallData;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardImages;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfallData;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.domain.set.SetType.alchemy;
import static fr.ignishky.mtgcollection.domain.set.SetType.expansion;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;

public class ScryfallFixtures {

    private static final Set aFutureSet = new SetGenerator()
            .withSetCode(new SetCode("wtf"))
            .withSetName(new SetName("NON-EXISTING SET"))
            .withReleaseDate("9999-12-31")
            .withSetType(expansion)
            .withCardCount(63)
            .withSetIcon(new SetIcon("icon3"))
            .generate();

    private static final Set aDigitalSet = new SetGenerator()
            .withSetCode(new SetCode("mtga"))
            .withSetName(new SetName("DIGITAL SET"))
            .withReleaseDate("2021-12-11")
            .withSetType(alchemy)
            .withCardCount(13)
            .withSetIcon(new SetIcon("icon4"))
            .withDigital(true)
            .generate();

    public static final SetScryfall aScryfallSets = new SetScryfall(List.of(getSetData(aFutureSet), getSetData(aFailedSet), getSetData(StreetOfNewCapenna), getSetData(Kaldheim), getSetData(aDigitalSet)));

    public static final CardScryfall aScryfallCards = new CardScryfall(null, List.of(getCardData(aCard), getCardData(anExtraCard)));
    public static final CardScryfall anotherScryfallCards = new CardScryfall("https://scryfall.mtg.test/page%3A2", List.of(getCardData(anotherCard)));
    public static final CardScryfall anotherScryfallCards2 = new CardScryfall(null, List.of(getCardData(anotherCard2)));

    private static SetScryfallData getSetData(Set aSet) {
        return new SetScryfallData(
                aSet.id().id().toString(),
                aSet.code().value(),
                aSet.name().value(),
                aSet.isDigital(),
                aSet.parentSetCode().map(SetCode::value).getOrNull(),
                aSet.blockCode().map(SetCode::value).getOrNull(),
                aSet.releasedDate(),
                aSet.setType().name(),
                aSet.cardCount(),
                aSet.icon().url()
        );
    }

    private static CardScryfallData getCardData(Card aCard) {
        return new CardScryfallData(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.setCode().value(),
                new CardImages(aCard.cardImage().image()),
                null
        );
    }

}
