package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import io.vavr.collection.List;

public record GetCardsQuery(
        SetCode code
) implements Query<List<Card>> {
}
