package fr.ignishky.mtgcollection.infrastructure.spi.mongo;

import fr.ignishky.mtgcollection.domain.set.model.Set;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static io.vavr.control.Option.ofOptional;

@Repository
public interface SetMongoRepository extends SetRepository, MongoRepository<SetDocument, UUID> {

    @Override
    default void save(List<Set> sets) {
        saveAll(sets.map(MongoDocumentMapper::toDocument));
    }

    @Override
    default void update(Set set) {
        saveAll(List.of(set).map(MongoDocumentMapper::toDocument));
    }

    @Override
    default List<Set> getAll() {
        return List.ofAll(findAll()).map(MongoDocumentMapper::toSet);
    }

    @Override
    default Option<Set> get(SetCode code) {
        return ofOptional(findOne(Example.of(SetDocument.from(code))))
                .map(MongoDocumentMapper::toSet);
    }

}
