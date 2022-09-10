package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetIcon;
import fr.ignishky.mtgcollection.domain.set.SetName;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.*;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.domain.set.SetType.ALCHEMY;
import static fr.ignishky.mtgcollection.domain.set.SetType.EXPANSION;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;

public class ScryfallFixtures {

    private ScryfallFixtures() {
    }

    private static final Set aFutureSet = new SetGenerator()
            .withSetCode(new SetCode("wtf"))
            .withSetName(new SetName("NON-EXISTING SET"))
            .withReleaseDate("9999-12-31")
            .withSetType(EXPANSION)
            .withCardCount(63)
            .withSetIcon(new SetIcon("icon3"))
            .generate();

    private static final Set aDigitalSet = new SetGenerator()
            .withSetCode(new SetCode("mtga"))
            .withSetName(new SetName("DIGITAL SET"))
            .withReleaseDate("2021-12-11")
            .withSetType(ALCHEMY)
            .withCardCount(13)
            .withSetIcon(new SetIcon("icon4"))
            .withDigital(true)
            .generate();

    public static final SetScryfall aScryfallSets = new SetScryfall(List.of(
            getSetData(aFutureSet),
            getSetData(aFailedSet),
            getSetData(StreetOfNewCapenna),
            getSetData(Kaldheim),
            getSetData(aDigitalSet)
    ));

    public static final CardScryfall aScryfallCards = new CardScryfall(null, List.of(getCardData(aCard, false), getCardData(anExtraCard, false)));
    public static final CardScryfall anotherScryfallCards = new CardScryfall("https://scryfall.mtg.test/page%3A2", List.of(getCardData(anotherCard, false), getCardData(anotherCard3, true)));
    public static final CardScryfall anotherScryfallCards2 = new CardScryfall(null, List.of(getCardData(anotherCard2, false)));

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

    private static CardScryfallData getCardData(Card aCard, boolean isDigital) {
        return new CardScryfallData(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.setCode().value(),
                isDigital,
                new CardImages(aCard.cardImage().image()),
                null,
                new Prices(aCard.prices().head().eur(), aCard.prices().head().eurFoil())
        );
    }

}
