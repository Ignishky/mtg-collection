package fr.ignishky.mtgcollection.domain.card.command;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.model.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.Command;

public record DeleteOwnCardCommand(
        CardId cardId
) implements Command<Card> {

}
