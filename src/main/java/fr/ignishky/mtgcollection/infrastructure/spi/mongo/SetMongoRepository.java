package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SetMongoRepository extends SetRepository, MongoRepository<SetDocument, UUID> {

    @Override
    default void save(List<Set> sets) {
        saveAll(sets.map(SetDocument::fromSet));
    }

    @Override
    default List<Set> getAll() {
        return List.ofAll(findAll()).map(SetDocument::toSet);
    }

}
