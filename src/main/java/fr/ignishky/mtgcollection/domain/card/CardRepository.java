package fr.ignishky.mtgcollection.domain.card;

import io.vavr.collection.List;
import io.vavr.control.Option;

public interface CardRepository {

    void save(Card card);

    void save(List<Card> cards);

    Option<Card> get(CardId cardId);
}
