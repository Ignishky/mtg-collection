package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.command.collection.AddOwnCardCommand;
import fr.ignishky.mtgcollection.command.collection.DeleteOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionRequest;
import fr.ignishky.mtgcollection.query.card.GetCardsQuery;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse.toCardResponse;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.*;

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
                .cards()
                .map(CardResponse::toCardResponse);
        LOGGER.info("Respond to `GET /collection` with {} cards", ownedCards.size());
        return ok(ownedCards);
    }

    @Override
    public ResponseEntity<CardResponse> addCard(String cardId, CollectionRequest request) {
        LOGGER.info("Received call to `PUT /collection/{}` with body {}", cardId, request);
        return commandBus.dispatch(new AddOwnCardCommand(new CardId(UUID.fromString(cardId)), request.isFoiled()))
                .fold(
                        cause -> failure(cardId, cause),
                        CollectionController::success
                );
    }

    @Override
    public ResponseEntity<CardResponse> deleteCard(String cardId) {
        LOGGER.info("Received call to `DELETE /collection/{}`", cardId);
        return commandBus.dispatch(new DeleteOwnCardCommand(new CardId(UUID.fromString(cardId))))
                .fold(
                        cause -> failure(cardId, cause),
                        CollectionController::success
                );
    }

    private static ResponseEntity<CardResponse> success(Card card) {
        LOGGER.info("Respond to `PUT /collection/{}` with a success", card.id());
        return ok().body(toCardResponse(card));
    }

    private static ResponseEntity<CardResponse> failure(String cardId, Throwable cause) {
        if (cause instanceof NoCardFoundException) {
            LOGGER.info("Respond to `PUT /collection/{}` with a 404", cardId);
            return notFound().build();
        } else {
            LOGGER.error("Respond to `PUT /collection/{}` with a 500", cardId);
            return internalServerError().build();
        }
    }

}
