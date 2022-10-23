package fr.ignishky.mtgcollection.domain.card.command;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.Command;

public record AddOwnCardCommand(
        CardId cardId,
        boolean isOwnedFoil
) implements Command<Card> {

}
