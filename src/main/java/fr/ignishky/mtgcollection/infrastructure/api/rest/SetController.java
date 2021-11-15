package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import org.springframework.stereotype.Controller;

@Controller
record SetController(
        CommandBus commandBus
) implements AnnotatedService, SetApi {

    public void loadAll() {
        commandBus.dispatch(new RefreshSetCommand());
    }

}
