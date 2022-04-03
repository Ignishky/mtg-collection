package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.command.collection.AddOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.CardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.CardResponse.toCardResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class CollectionController implements CollectionApi {

    private final CommandBus commandBus;

    public CollectionController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public ResponseEntity<CardResponse> addCard(String cardId, CollectionRequestBody body){
        var dispatch = commandBus.dispatch(new AddOwnCardCommand(new CardId(UUID.fromString(cardId)), body.isFoiled()));

        return dispatch.isSuccess() ? ok().contentType(APPLICATION_JSON).body(toCardResponse(dispatch.get())) : notFound().build();
    }

}
