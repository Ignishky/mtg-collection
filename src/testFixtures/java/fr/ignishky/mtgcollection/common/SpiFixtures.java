package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;

public class SpiFixtures {

    public static SetScryfall aScryfallSets = new SetScryfall(List.of(
                new SetScryfall.ScryfallData(aFutureSet.id().id().toString(), aFutureSet.code().value(), aFutureSet.name().value(), aFutureSet.releasedDate(), aFutureSet.icon().url()),
                new SetScryfall.ScryfallData(aSet.id().id().toString(), aSet.code().value(), aSet.name().value(), aSet.releasedDate(), aSet.icon().url()),
                new SetScryfall.ScryfallData(anotherSet.id().id().toString(), anotherSet.code().value(), anotherSet.name().value(), anotherSet.releasedDate(), anotherSet.icon().url())));

    public static SetDocument aMongoSet = new SetDocument(aSet.id().id(), aSet.code().value(), aSet.name().value(), aSet.icon().url());

    public static SetDocument anotherMongoSet = new SetDocument(anotherSet.id().id(), anotherSet.code().value(), anotherSet.name().value(), anotherSet.icon().url());

}
