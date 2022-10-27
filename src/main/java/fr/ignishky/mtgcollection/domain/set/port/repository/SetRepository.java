package fr.ignishky.mtgcollection.domain.set.port.repository;

import fr.ignishky.mtgcollection.domain.set.model.Set;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface SetRepository {

    void save(List<Set> sets);

    void update(Set set);

    List<Set> getAll();

    Option<Set> get(SetCode code);

}
