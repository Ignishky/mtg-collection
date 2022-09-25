package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetIcon;
import fr.ignishky.mtgcollection.domain.set.SetName;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.*;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.domain.set.SetType.ALCHEMY;
import static fr.ignishky.mtgcollection.domain.set.SetType.EXPANSION;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SetGenerator;

public class ScryfallFixtures {

    private ScryfallFixtures() {
    }

    public static final Set aFutureSet = new SetGenerator()
            .withSetCode(new SetCode("wtf"))
            .withSetName(new SetName("NON-EXISTING SET"))
            .withReleaseDate("9999-12-31")
            .withSetType(EXPANSION)
            .withCardCount(63)
            .withSetIcon(new SetIcon("icon3"))
            .generate();

    public static final Set aDigitalSet = new SetGenerator()
            .withSetCode(new SetCode("mtga"))
            .withSetName(new SetName("DIGITAL SET"))
            .withReleaseDate("2021-12-11")
            .withSetType(ALCHEMY)
            .withCardCount(13)
            .withSetIcon(new SetIcon("icon4"))
            .withDigital(true)
            .generate();

    public static final Set aFailedSet = new SetGenerator()
            .withSetCode(new SetCode("fail"))
            .withSetName(new SetName("FAILED SET"))
            .withReleaseDate("2021-12-01")
            .withSetType(EXPANSION)
            .withCardCount(1)
            .withSetIcon(new SetIcon("icon5"))
            .generate();

    public static final CardScryfall ledgerShredderWithoutPrice = new CardScryfall(null, List.of(ledgerShredderWithoutPrice()));
    public static final CardScryfall singleSNCPage = new CardScryfall(null, List.of(
            getCardData(ledgerShredder.withPrices(new Price("3.50", "4.50"))),
            getCardData(depopulate))
    );
    public static final CardScryfall firstKHMPage = new CardScryfall("https://scryfall.mtg.test/page%3A2", List.of(getCardData(vorinclex), getCardData(aDigitalCard, true)));
    public static final CardScryfall lastKHMPage = new CardScryfall(null, List.of(getCardData(esika)));

    public static SetScryfallData getSetData(Set aSet) {
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

    private static CardScryfallData ledgerShredderWithoutPrice() {
        return new CardScryfallData(
                ledgerShredder.id().id(),
                ledgerShredder.cardName().name(),
                ledgerShredder.setCode().value(),
                false,
                new CardImages(ledgerShredder.cardImage().image()),
                null,
                new Prices(null, null)
        );
    }

    private static CardScryfallData getCardData(Card aCard) {
        return new CardScryfallData(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.setCode().value(),
                false,
                new CardImages(aCard.cardImage().image()),
                null,
                new Prices(aCard.prices().eur(), aCard.prices().eurFoil())
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
                new Prices(aCard.prices().eur(), aCard.prices().eurFoil())
        );
    }

}
