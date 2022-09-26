package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.ApiResponseMapper;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse;
import fr.ignishky.mtgcollection.query.card.GetCardsQuery;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.notFound;
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
    public ResponseEntity<SetResponse> getCards(String setCode) {
        LOGGER.info("Received call to `GET /sets/{}`", setCode);
        var getCardsResponse = queryBus.dispatch(new GetCardsQuery(Option.of(new SetCode(setCode)), false));
        var cards = getCardsResponse.cards()
                .map(ApiResponseMapper::toCardSummary);
        LOGGER.info("Respond to `GET /sets/{}` with {} cards", setCode, cards.size());

        return cards.isEmpty()
                ? notFound().build()
                : ok(new SetResponse(
                getCardsResponse.setName().getOrNull(),
                cards.size(),
                getCardsResponse.nbOwned(),
                getCardsResponse.nbOwnedFoil(),
                getCardsResponse.maxValue(),
                getCardsResponse.ownedValue(),
                cards));
    }

}
