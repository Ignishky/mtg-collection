package fr.ignishky.mtgcollection.domain.card;

import io.vavr.collection.List;

public interface CardRepository {

    void save(List<Card> cards);

}
