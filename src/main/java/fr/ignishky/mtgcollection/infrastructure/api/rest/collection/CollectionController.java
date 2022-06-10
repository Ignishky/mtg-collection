package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.command.collection.AddOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.query.card.GetCardsQuery;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.CardResponse.toCardResponse;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class CollectionController implements CollectionApi {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public CollectionController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @Override
    public ResponseEntity<List<CardResponse>> getCollection() {
        var dispatch = queryBus.dispatch(new GetCardsQuery(Option.none(), true))
                .map(CardResponse::toCardResponse);
        return ok(dispatch);
    }

    @Override
    public ResponseEntity<CardResponse> addCard(String cardId, CollectionRequestBody body){
        var dispatch = commandBus.dispatch(new AddOwnCardCommand(new CardId(UUID.fromString(cardId)), body.isFoiled()));

        return dispatch.isSuccess() ? ok().body(toCardResponse(dispatch.get())) : notFound().build();
    }

}
