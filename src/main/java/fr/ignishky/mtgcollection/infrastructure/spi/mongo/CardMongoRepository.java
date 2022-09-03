package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toCardDocument;

@Repository
public interface CardMongoRepository extends CardRepository, MongoRepository<CardDocument, UUID> {

    @Override
    default void save(Card card) {
        save(toCardDocument(card));
    }

    @Override
    default void save(List<Card> cards) {
        saveAll(cards.map(MongoDocumentMapper::toCardDocument));
    }

    @Override
    default Option<Card> get(CardId cardId) {
        return Option.ofOptional(findById(cardId.id())).map(MongoDocumentMapper::toCard);
    }

}
