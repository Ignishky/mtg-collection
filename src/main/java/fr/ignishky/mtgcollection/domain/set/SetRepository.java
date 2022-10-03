package fr.ignishky.mtgcollection.domain.set;

import io.vavr.collection.List;
import io.vavr.control.Option;

public interface SetRepository {

    void save(List<Set> sets);

    void update(Set set);

    List<Set> getAll();

    Option<Set> get(SetCode code);

}
