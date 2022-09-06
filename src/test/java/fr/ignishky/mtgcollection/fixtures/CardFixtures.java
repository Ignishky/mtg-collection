package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;

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
            false,
            false);
    public static final Card aOwnedCard = new Card(
            aCard.id(),
            aCard.setCode(),
            aCard.cardName(),
            aCard.cardImage(),
            true,
            true);

    public static final Card anExtraCard = new Card(
            CardId.fromString("e8bf4f70-9f31-4936-86a8-4d126a86e102"),
            StreetOfNewCapenna.code(),
            new CardName("an-extra-card-name"),
            new CardImage("an-extra-card-image"),
            false,
            false);
    public static final Card anExtraOwnedCard = new Card(
            anExtraCard.id(),
            anExtraCard.setCode(),
            anExtraCard.cardName(),
            anExtraCard.cardImage(),
            true,
            false);
    public static final Card anotherCard = new Card(
            new CardId(randomUUID()),
            Kaldheim.code(),
            new CardName("another-card-name"),
            new CardImage("another-card-image"),
            false,
            false);
    public static final Card anotherCard2 = new Card(
            new CardId(randomUUID()),
            Kaldheim.code(),
            new CardName("another-card-name2"),
            new CardImage("another-card-image2"),
            false,
            false);

}
