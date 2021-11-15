package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.framework.cqrs.command.Command;

public record RefreshSetCommand() implements Command<Void> {
}
