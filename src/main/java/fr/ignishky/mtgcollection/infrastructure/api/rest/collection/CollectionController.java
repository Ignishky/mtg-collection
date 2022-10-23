package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.command.AddOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.command.DeleteOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.domain.collection.query.GetCollectionQuery;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionRequest;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionResponse;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.mapper.CollectionMapper.toCardResponse;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.mapper.CollectionMapper.toCollection;
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
    public ResponseEntity<CollectionResponse> getCollection() {
        LOGGER.info("Received call to `GET /collection`");
        var collection = queryBus.dispatch(new GetCollectionQuery());
        LOGGER.info("Respond to `GET /collection` with {} cards", collection.cards().size());
        return ok(toCollection(collection));
    }

    @Override
    public ResponseEntity<CardResponse> addCard(UUID cardId, CollectionRequest request) {
        LOGGER.info("Received call to `PUT /collection/{}` with body {}", cardId, request);
        return commandBus.dispatch(new AddOwnCardCommand(new CardId(cardId), request.isOwnedFoil()))
                .fold(
                        cause -> failure("PUT", cardId, cause),
                        card -> success("PUT", card)
                );
    }

    @Override
    public ResponseEntity<CardResponse> deleteCard(UUID cardId) {
        LOGGER.info("Received call to `DELETE /collection/{}`", cardId);
        return commandBus.dispatch(new DeleteOwnCardCommand(new CardId(cardId)))
                .fold(
                        cause -> failure("DELETE", cardId, cause),
                        card -> success("DELETE", card)
                );
    }

    private static ResponseEntity<CardResponse> success(String httpVerb, Card card) {
        LOGGER.info("Respond to `{} /collection/{}` with a success", httpVerb, card.id());
        return ok().body(toCardResponse(card));
    }

    private static ResponseEntity<CardResponse> failure(String httpVerb, UUID cardId, Throwable cause) {
        if (cause instanceof NoCardFoundException) {
            LOGGER.info("Respond to `{} /collection/{}` with a 404", httpVerb, cardId);
            return notFound().build();
        } else {
            LOGGER.error("Respond to `{} /collection/{}` with a 500", httpVerb, cardId);
            return internalServerError().build();
        }
    }

}
