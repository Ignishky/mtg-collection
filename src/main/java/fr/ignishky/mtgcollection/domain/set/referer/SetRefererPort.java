package fr.ignishky.mtgcollection.domain.set.referer;

import io.vavr.collection.List;

public interface SetRefererPort {

    List<? extends SetReferer> loadAll();

}
