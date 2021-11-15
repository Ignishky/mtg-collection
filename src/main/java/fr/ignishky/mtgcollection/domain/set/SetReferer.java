package fr.ignishky.mtgcollection.domain.set;

import io.vavr.collection.List;

public interface SetReferer {

    List<Set> loadAll();

}
