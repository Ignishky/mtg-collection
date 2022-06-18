package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall.ScryfallData.Images;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.fixtures.DomainFixtures.*;

public class SpiFixtures {

    public static final SetScryfall aScryfallSets = new SetScryfall(List.of(getSetData(aFutureSet), getSetData(aSet), getSetData(anotherSet), getSetData(aDigitalSet)));

    public static final CardScryfall aScryfallCards = new CardScryfall(null, List.of(getCardData(aCard), getCardData(anExtraCard)));
    public static final CardScryfall anotherScryfallCards = new CardScryfall("https://scryfall.mtg.test/page%3A2", List.of(getCardData(anotherCard)));
    public static final CardScryfall anotherScryfallCards2 = new CardScryfall(null, List.of(getCardData(anotherCard2)));

    private static SetScryfall.ScryfallData getSetData(Set aSet) {
        return new SetScryfall.ScryfallData(
                aSet.id().id().toString(),
                aSet.code().value(),
                aSet.name().value(),
                aSet.isDigital(),
                aSet.releasedDate(),
                aSet.cardCount(),
                aSet.icon().url());
    }

    private static CardScryfall.ScryfallData getCardData(Card aCard) {
        return new CardScryfall.ScryfallData(
                aCard.id().id(),
                aCard.cardName().name(),
                aCard.setCode().value(),
                new Images(aCard.cardImage().image()),
                null);
    }

}
