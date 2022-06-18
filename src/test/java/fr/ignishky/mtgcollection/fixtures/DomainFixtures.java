package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.*;

import static java.util.UUID.randomUUID;

public class DomainFixtures {

    public static final Set aSet = new Set(
            new SetId(randomUUID()),
            new SetCode("a-set-code"),
            new SetName("a-set-name"),
            false,
            "2011-09-12",
            365,
            new SetIcon("a-set-icon"));
    public static final Set anotherSet = new Set(
            new SetId(randomUUID()),
            new SetCode("another-set-code"),
            new SetName("another-set-name"),
            false,
            "2018-10-12",
            165,
            new SetIcon("another-set-icon"));
    public static final Set aFutureSet = new Set(
            new SetId(randomUUID()),
            new SetCode("wtf"),
            new SetName("NON-EXISTING SETS"),
            false,
            "9999-12-31",
            63,
            new SetIcon("icon3"));
    public static final Set aDigitalSet = new Set(
            new SetId(randomUUID()),
            new SetCode("mtga"),
            new SetName("DIGITAL SET"),
            true,
            "2021-12-11",
            13,
            new SetIcon("icon4"));

    public static final Card aCard = new Card(
            CardId.fromString("66b5dddd-af38-494e-9047-f7311ff539da"),
            aSet.code(),
            new CardName("a-card-name"),
            new CardImage("a-card-image"),
            null,
            null);
    public static final Card aOwnedCard = new Card(
            aCard.id(),
            aCard.setCode(),
            aCard.cardName(),
            aCard.cardImage(),
            true,
            true);

    public static final Card anExtraCard = new Card(
            CardId.fromString("e8bf4f70-9f31-4936-86a8-4d126a86e102"),
            aSet.code(),
            new CardName("an-extra-card-name"),
            new CardImage("an-extra-card-image"),
            null,
            null);
    public static final Card anExtraOwnedCard = new Card(
            anExtraCard.id(),
            anExtraCard.setCode(),
            anExtraCard.cardName(),
            anExtraCard.cardImage(),
            true,
            false);
    public static final Card anotherCard = new Card(
            new CardId(randomUUID()),
            anotherSet.code(),
            new CardName("another-card-name"),
            new CardImage("another-card-image"),
            null,
            null);
    public static final Card anotherCard2 = new Card(
            new CardId(randomUUID()),
            anotherSet.code(),
            new CardName("another-card-name2"),
            new CardImage("another-card-image2"),
            null,
            null);

}
