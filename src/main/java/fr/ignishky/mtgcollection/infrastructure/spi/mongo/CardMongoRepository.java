package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.CardId;
import fr.ignishky.mtgcollection.domain.card.port.repository.CardRepository;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toDocument;

@Repository
public interface CardMongoRepository extends CardRepository, MongoRepository<CardDocument, UUID> {

    @Override
    default void save(Card card) {
        save(toDocument(card));
    }

    @Override
    default void save(List<Card> cards) {
        saveAll(cards.map(MongoDocumentMapper::toDocument));
    }

    @Override
    default Option<Card> get(CardId cardId) {
        return Option.ofOptional(findById(cardId.id())).map(MongoDocumentMapper::toCard);
    }

}
