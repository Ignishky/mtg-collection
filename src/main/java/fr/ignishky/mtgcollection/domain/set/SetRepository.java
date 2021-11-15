package fr.ignishky.mtgcollection.domain.set;

import io.vavr.collection.List;

public interface SetRepository {

    void save(List<Set> blocks);

}
