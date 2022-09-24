package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.*;
import io.vavr.collection.List;

import java.time.LocalDate;

import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;
import static java.util.UUID.randomUUID;

public class CardFixtures {

    private CardFixtures() {
    }

    public static final Card ledgerShredder = new Card(
            CardId.fromString("66b5dddd-af38-494e-9047-f7311ff539da"),
            SNC.code(),
            new CardName("Ledger Shredder"),
            new CardImage("LedgerShredder.png"),
            List.of(new Price(LocalDate.of(2022, 8, 25), "1.50", null)),
            false,
            false);
    public static final Card ledgerShredderOwnedFoiled = new Card(
            ledgerShredder.id(),
            ledgerShredder.setCode(),
            ledgerShredder.cardName(),
            ledgerShredder.cardImage(),
            ledgerShredder.prices(),
            true,
            true);
    public static final Card anUpdatedOwnedCard = new Card(
            ledgerShredder.id(),
            ledgerShredder.setCode(),
            ledgerShredder.cardName(),
            ledgerShredder.cardImage(),
            ledgerShredder.prices().append(ledgerShredder.prices().head()),
            true,
            true);

    public static final Card depopulate = new Card(
            CardId.fromString("e8bf4f70-9f31-4936-86a8-4d126a86e102"),
            SNC.code(),
            new CardName("Depopulate"),
            new CardImage("Depopulate.png"),
            List.of(new Price(LocalDate.of(2021, 10, 12), "1.00", "2.00")),
            false,
            false);
    public static final Card depopulateOwned = new Card(
            depopulate.id(),
            depopulate.setCode(),
            depopulate.cardName(),
            depopulate.cardImage(),
            depopulate.prices(),
            true,
            false);
    public static final Card vorinclex = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("Vorinclex"),
            new CardImage("Vorinclex.png"),
            List.of(new Price(LocalDate.of(2022, 9, 1), "0.00", "0.00")),
            false,
            false);
    public static final Card esika = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("Esika"),
            new CardImage("Esika.png"),
            List.of(new Price(LocalDate.of(2022, 9, 2), "0.00", "0.00")),
            false,
            false);
    static final Card aDigitalCard = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("another-card-name3"),
            new CardImage("another-card-image3"),
            List.of(new Price(LocalDate.of(2022, 9, 3), null, null)),
            false,
            false);

}
