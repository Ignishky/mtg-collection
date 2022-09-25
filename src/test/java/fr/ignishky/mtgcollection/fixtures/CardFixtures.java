package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.*;

import static fr.ignishky.mtgcollection.fixtures.SetFixtures.KHM;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

public class CardFixtures {

    private CardFixtures() {
    }

    public static final Card ledgerShredder = new Card(
            CardId.fromString("66b5dddd-af38-494e-9047-f7311ff539da"),
            SNC.code(),
            new CardName("Ledger Shredder"),
            new CardImage("LedgerShredder.png"),
            new Price("1.50", null),
            false,
            false,
            now()
    );
    public static final Card ledgerShredderOwnedFoiled = new Card(
            ledgerShredder.id(),
            ledgerShredder.setCode(),
            ledgerShredder.cardName(),
            ledgerShredder.cardImage(),
            ledgerShredder.prices(),
            true,
            true,
            ledgerShredder.lastUpdate()
    );
    public static final Card anUpdatedOwnedCard = new Card(
            ledgerShredder.id(),
            ledgerShredder.setCode(),
            ledgerShredder.cardName(),
            ledgerShredder.cardImage(),
            new Price("3.50", "4.50"),
            true,
            true,
            ledgerShredder.lastUpdate()
    );

    public static final Card depopulate = new Card(
            CardId.fromString("e8bf4f70-9f31-4936-86a8-4d126a86e102"),
            SNC.code(),
            new CardName("Depopulate"),
            new CardImage("Depopulate.png"),
            new Price("1.00", "2.00"),
            false,
            false,
            now()
    );
    public static final Card depopulateOwned = new Card(
            depopulate.id(),
            depopulate.setCode(),
            depopulate.cardName(),
            depopulate.cardImage(),
            depopulate.prices(),
            true,
            false,
            depopulate.lastUpdate()
    );
    public static final Card vorinclex = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("Vorinclex"),
            new CardImage("Vorinclex.png"),
            new Price("0.00", "0.00"),
            false,
            false,
            now()
    );
    public static final Card esika = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("Esika"),
            new CardImage("Esika.png"),
            new Price("0.00", "0.00"),
            false,
            false,
            now()
    );
    static final Card aDigitalCard = new Card(
            new CardId(randomUUID()),
            KHM.code(),
            new CardName("another-card-name3"),
            new CardImage("another-card-image3"),
            new Price(null, null),
            false,
            false,
            now()
    );

}
