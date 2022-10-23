package fr.ignishky.mtgcollection.domain.card.command;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.Command;

public record DeleteOwnCardCommand(
        CardId cardId
) implements Command<Card> {

}
