package fr.ignishky.mtgcollection.domain.card.port.referer;

import fr.ignishky.mtgcollection.domain.card.port.referer.model.CardReferer;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import io.vavr.collection.List;

public interface CardRefererPort {

    List<? extends CardReferer> load(SetCode setCode);

}
