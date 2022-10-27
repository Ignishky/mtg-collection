package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.domain.set.command.RefreshSetCommand;
import fr.ignishky.mtgcollection.domain.set.query.GetSetQuery;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.mapper.SetMapper.toSetResponse;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;

@Controller
class SetController implements SetApi {

    private static final Logger LOGGER = getLogger(SetController.class);

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    SetController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    public ResponseEntity<Void> loadAll() {
        commandBus.dispatch(new RefreshSetCommand());
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<SetResponse> getSet(String setCode) {
        LOGGER.info("Received call to `GET /sets/{}`", setCode);
        var set = queryBus.dispatch(new GetSetQuery(new SetCode(setCode)));
        LOGGER.info("Respond to `GET /sets/{}` with {} cards", setCode, set.cards().size());
        return ok(toSetResponse(set));
    }

}
