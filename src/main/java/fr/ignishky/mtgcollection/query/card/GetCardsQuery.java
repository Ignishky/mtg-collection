package fr.ignishky.mtgcollection.query.card;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.Query;
import io.vavr.collection.List;
import io.vavr.control.Option;

public record GetCardsQuery(
        Option<SetCode> code,
        boolean owned
) implements Query<List<Card>> {
}
