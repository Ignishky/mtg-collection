package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.*;
import io.vavr.collection.List;

import java.time.LocalDate;

import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;
import static java.util.UUID.randomUUID;

public class CardFixtures {

    private CardFixtures() {
    }

    public static final Card aCard = new Card(
            CardId.fromString("66b5dddd-af38-494e-9047-f7311ff539da"),
            StreetOfNewCapenna.code(),
            new CardName("a-card-name"),
            new CardImage("a-card-image"),
            List.of(new Price(LocalDate.of(2022, 8, 25), "1.50", null)),
            false,
            false);
    public static final Card aOwnedCard = new Card(
            aCard.id(),
            aCard.setCode(),
            aCard.cardName(),
            aCard.cardImage(),
            aCard.prices(),
            true,
            true);

    public static final Card anExtraCard = new Card(
            CardId.fromString("e8bf4f70-9f31-4936-86a8-4d126a86e102"),
            StreetOfNewCapenna.code(),
            new CardName("an-extra-card-name"),
            new CardImage("an-extra-card-image"),
            List.of(new Price(LocalDate.of(2021, 10, 12), "1.00", "2.00")),
            false,
            false);
    public static final Card anExtraOwnedCard = new Card(
            anExtraCard.id(),
            anExtraCard.setCode(),
            anExtraCard.cardName(),
            anExtraCard.cardImage(),
            anExtraCard.prices(),
            true,
            false);
    public static final Card anotherCard = new Card(
            new CardId(randomUUID()),
            Kaldheim.code(),
            new CardName("another-card-name"),
            new CardImage("another-card-image"),
            List.of(new Price(LocalDate.of(2022, 9, 1), "0.00", "0.00")),
            false,
            false);
    public static final Card anotherCard2 = new Card(
            new CardId(randomUUID()),
            Kaldheim.code(),
            new CardName("another-card-name2"),
            new CardImage("another-card-image2"),
            List.of(new Price(LocalDate.of(2022, 9, 2), "0.00", "0.00")),
            false,
            false);
    public static final Card anotherCard3 = new Card(
            new CardId(randomUUID()),
            Kaldheim.code(),
            new CardName("another-card-name3"),
            new CardImage("another-card-image3"),
            List.of(new Price(LocalDate.of(2022, 9, 3), null, null)),
            false,
            false);

}
