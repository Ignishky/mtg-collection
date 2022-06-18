package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.command.collection.AddOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionRequest;
import fr.ignishky.mtgcollection.query.card.GetCardsQuery;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse.toCardResponse;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class CollectionController implements CollectionApi {

    private static final Logger LOGGER = getLogger(CollectionController.class);

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public CollectionController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @Override
    public ResponseEntity<List<CardResponse>> getCollection() {
        LOGGER.info("Received call to `GET /collection`");
        var ownedCards = queryBus.dispatch(new GetCardsQuery(Option.none(), true))
                .map(CardResponse::toCardResponse);
        LOGGER.info("Respond to `GET /collection` with {} cards", ownedCards.size());
        return ok(ownedCards);
    }

    @Override
    public ResponseEntity<CardResponse> addCard(String cardId, CollectionRequest request) {
        LOGGER.info("Received call to `PUT /collection/{}` with body {}", cardId, request);
        var dispatch = commandBus.dispatch(new AddOwnCardCommand(new CardId(UUID.fromString(cardId)), request.isFoiled()));

        return dispatch.isSuccess()
                ? success(cardId, dispatch)
                : failure(cardId);
    }

    private static ResponseEntity<CardResponse> success(String cardId, Try<Card> dispatch) {
        LOGGER.info("Respond to `PUT /collection/{}` with a success", cardId);
        return ok().body(toCardResponse(dispatch.get()));
    }

    private static ResponseEntity<CardResponse> failure(String cardId) {
        LOGGER.info("Respond to `PUT /collection/{}` with a failure", cardId);
        return notFound().build();
    }

}
