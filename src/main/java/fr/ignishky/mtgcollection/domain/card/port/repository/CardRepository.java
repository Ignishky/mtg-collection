package fr.ignishky.mtgcollection.domain.card.port.repository;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.CardId;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface CardRepository {

    void save(Card card);

    void save(List<Card> cards);

    Option<Card> get(CardId cardId);

}
