package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall.ScryfallData.Images;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;

public class SpiFixtures {

    public static SetScryfall aScryfallSets = new SetScryfall(List.of(
            new SetScryfall.ScryfallData(aFutureSet.id().id().toString(), aFutureSet.code().value(), aFutureSet.name().value(), aFutureSet.releasedDate(), aFutureSet.cardCount(), aFutureSet.icon().url()),
            new SetScryfall.ScryfallData(aSet.id().id().toString(), aSet.code().value(), aSet.name().value(), aSet.releasedDate(), aSet.cardCount(), aSet.icon().url()),
            new SetScryfall.ScryfallData(anotherSet.id().id().toString(), anotherSet.code().value(), anotherSet.name().value(), anotherSet.releasedDate(), anotherSet.cardCount(), anotherSet.icon().url())));

    public static CardScryfall aScryfallCards = new CardScryfall(List.of(
            new CardScryfall.ScryfallData(aCard.id().id(), aCard.cardName().name(), aCard.setCode().value(), new Images(aCard.cardImage().image()), null),
            new CardScryfall.ScryfallData(anExtraCard.id().id(), anExtraCard.cardName().name(), anExtraCard.setCode().value(), new Images(anExtraCard.cardImage().image()), null)
    ));

    public static CardScryfall anotherScryfallCards = new CardScryfall(List.of(
            new CardScryfall.ScryfallData(anotherCard.id().id(), anotherCard.cardName().name(), anotherCard.setCode().value(), new Images(anotherCard.cardImage().image()), null)
    ));

    public static SetDocument aMongoSet = new SetDocument(aSet.id().id(), aSet.code().value(), aSet.name().value(), aSet.releasedDate(), aSet.cardCount(), aSet.icon().url());
    public static SetDocument anotherMongoSet = new SetDocument(anotherSet.id().id(), anotherSet.code().value(), anotherSet.name().value(), anotherSet.releasedDate(), anotherSet.cardCount(), anotherSet.icon().url());

    public static CardDocument aMongoCard = new CardDocument(aCard.id().id(), aCard.setCode().value(), aCard.cardName().name(), aCard.cardImage().image());
    public static CardDocument anotherMongoCard = new CardDocument(anotherCard.id().id(), anotherCard.setCode().value(), anotherCard.cardName().name(), anotherCard.cardImage().image());
    public static CardDocument anExtraMongoCard = new CardDocument(anExtraCard.id().id(), anExtraCard.setCode().value(), anExtraCard.cardName().name(), anExtraCard.cardImage().image());
}
