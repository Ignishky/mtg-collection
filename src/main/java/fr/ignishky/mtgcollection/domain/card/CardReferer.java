package fr.ignishky.mtgcollection.domain.card;

import fr.ignishky.mtgcollection.domain.set.SetCode;
import io.vavr.collection.List;

public interface CardReferer {

    List<Card> load(SetCode setCode);

}
