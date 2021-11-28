package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import io.vavr.collection.List;
import org.springframework.stereotype.Controller;

@Controller
record SetController(
        CommandBus commandBus,
        QueryBus queryBus
) implements SetApi {

    public void loadAll() {
        commandBus.dispatch(new RefreshSetCommand());
    }

    @Override
    public List<SetRest> getAll() {
        List<Set> sets = queryBus.dispatch(new GetSetsQuery());

        return sets.map(SetRest::fromSet);
    }

}
