package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;

import java.util.UUID;

public class SpiFixtures {

    public static SetScryfall aScryfallSets = new SetScryfall(List.of(
                new SetScryfall.ScryfallData("7d082e55-c58f-425f-b561-d25096089e58", "wtf", "NON-EXISTING SETS", "9999-12-31"),
                new SetScryfall.ScryfallData("a46f2063-5607-43aa-9ec6-c366c1afa02f", "code1", "name1", "2011-09-12"),
                new SetScryfall.ScryfallData("d95beffd-f2a2-4e31-8888-547db49cc3bf", "code2", "name2", "2018-10-12")));

    public static SetDocument aMongoSet = new SetDocument(UUID.fromString("a46f2063-5607-43aa-9ec6-c366c1afa02f"), "code1", "name1");

    public static SetDocument anotherMongoSet = new SetDocument(UUID.fromString("d95beffd-f2a2-4e31-8888-547db49cc3bf"), "code2", "name2");

}
