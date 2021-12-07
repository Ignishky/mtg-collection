package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardMongoRepository extends CardRepository, MongoRepository<CardDocument, UUID> {

    @Override
    default void save(List<Card> cards) {
        saveAll(cards.map(CardDocument::fromCard));
    }
}
