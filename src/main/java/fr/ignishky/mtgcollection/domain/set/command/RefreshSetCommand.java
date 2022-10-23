package fr.ignishky.mtgcollection.domain.set.command;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;

public record RefreshSetCommand() implements Command<Void> {

}
