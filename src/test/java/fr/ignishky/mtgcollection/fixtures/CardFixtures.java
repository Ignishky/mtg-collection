package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.model.*;
import io.vavr.collection.List;

import static fr.ignishky.mtgcollection.domain.card.model.Finish.*;
import static fr.ignishky.mtgcollection.domain.card.model.OwnState.NONE;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.KHM;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static java.time.LocalDate.now;

public class CardFixtures {

    public static final Card ledgerShredder = new Card(
            CardId.fromString("7ea4b5bc-18a4-45db-a56a-ab3f8bd2fb0d"),
            SNC.code(),
            new CardName("Ledger Shredder"),
            new CardImage("https://scryfall.mtgc.test/cards/ledgerShredder.png"),
            List.of(NON_FOIL, FOIL),
            new Price("1.50", null),
            NONE,
            now()
    );
    public static final Card depopulate = new Card(
            CardId.fromString("c53c1898-9107-4bf8-b249-d0502fb9596d"),
            SNC.code(),
            new CardName("Depopulate"),
            new CardImage("https://scryfall.mtgc.test/cards/depopulate.png"),
            List.of(NON_FOIL, FOIL),
            new Price("0.14", "0.63"),
            NONE,
            now()
    );
    public static final Card vorinclex = new Card(
            CardId.fromString("92613468-205e-488b-930d-11908477e9f8"),
            KHM.code(),
            new CardName("Vorinclex, Monstrous Raider"),
            new CardImage("https://scryfall.mtgc.test/cards/vorinclex.png"),
            List.of(NON_FOIL, FOIL),
            new Price("29.29", "31.00"),
            NONE,
            now()
    );
    public static final Card esika = new Card(
            CardId.fromString("f6cd7465-9dd0-473c-ac5e-dd9e2f22f5f6"),
            KHM.code(),
            new CardName("Esika, God of the Tree // The Prismatic Bridge"),
            new CardImage("https://scryfall.mtgc.test/cards/esika.png"),
            List.of(NON_FOIL, FOIL),
            new Price("8.96", "10.50"),
            NONE,
            now()
    );

}
