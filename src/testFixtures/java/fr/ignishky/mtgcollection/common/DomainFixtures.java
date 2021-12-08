package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.*;

import static java.util.UUID.randomUUID;

public class DomainFixtures {

    public static Set aSet = new Set(new SetId(randomUUID()), new SetCode("a-set-code"), new SetName("a-set-name"), "2011-09-12", 365, new SetIcon("a-set-icon"));
    public static Set anotherSet = new Set(new SetId(randomUUID()), new SetCode("another-set-code"), new SetName("another-set-name"), "2018-10-12", 165, new SetIcon("another-set-icon"));
    public static Set aFutureSet = new Set(new SetId(randomUUID()), new SetCode("wtf"), new SetName("NON-EXISTING SETS"), "9999-12-31", 63, new SetIcon("icon3"));

    public static Card aCard = new Card(new CardId(randomUUID()), aSet.code(), new CardName("a-card-name"), new CardImage("a-card-image"));
    public static Card anExtraCard = new Card(new CardId(randomUUID()), aSet.code(), new CardName("an-extra-card-name"), new CardImage("an-extra-card-image"));
    public static Card anotherCard = new Card(new CardId(randomUUID()), anotherSet.code(), new CardName("another-card-name"), new CardImage("another-card-image"));
    public static Card anotherCard2 = new Card(new CardId(randomUUID()), anotherSet.code(), new CardName("another-card-name2"), new CardImage("another-card-image2"));
}
