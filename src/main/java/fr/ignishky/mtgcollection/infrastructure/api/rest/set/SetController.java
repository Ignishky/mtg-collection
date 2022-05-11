package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse.SetSummary;
import fr.ignishky.mtgcollection.query.set.GetCardsQuery;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import io.vavr.collection.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
class SetController implements SetApi {

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
    public ResponseEntity<SetsResponse> getAll() {
        List<SetSummary> setsApis = queryBus.dispatch(new GetSetsQuery())
                .map(SetSummary::toSetSummary);
        return ok(new SetsResponse(setsApis));
    }

    @Override
    public ResponseEntity<List<CardResponse>> getCards(String setCode) {
        List<Card> cards = queryBus.dispatch(new GetCardsQuery(new SetCode(setCode)));
        return cards.isEmpty()
                ? notFound().build()
                : ok(cards.map(CardResponse::toCardResponse));
    }

}
