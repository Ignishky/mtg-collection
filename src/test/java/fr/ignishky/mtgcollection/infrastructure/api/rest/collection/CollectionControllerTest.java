package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.command.collection.AddOwnCardCommand;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static fr.ignishky.mtgcollection.fixtures.DomainFixtures.aCard;
import static io.vavr.control.Try.failure;
import static io.vavr.control.Try.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

class CollectionControllerTest {

    private final CommandBus commandBus = mock(CommandBus.class);

    private final CollectionController controller = new CollectionController(commandBus);

    @Test
    void should_return_notFound_when_cardId_is_unknown() {
        //GIVEN
        when(commandBus.dispatch(new AddOwnCardCommand(aCard.id(), false))).thenReturn(failure(new NoCardFoundException(aCard.id())));

        // WHEN
        ResponseEntity<CardResponse> response = controller.addCard(aCard.id().toString(), new CollectionRequestBody(false));

        // THEN
        assertThat(response).isEqualTo(notFound().build());
    }

    @Test
    void should_dispatch_addOwnCardCommand_for_given_card() {
        //GIVEN
        when(commandBus.dispatch(new AddOwnCardCommand(aCard.id(), true))).thenReturn(success(aCard));

        // WHEN
        ResponseEntity<CardResponse> response = controller.addCard(aCard.id().toString(), new CollectionRequestBody(true));

        // THEN
        assertThat(response).isEqualTo(ok()
                .contentType(APPLICATION_JSON)
                .body(new CardResponse(aCard.id().id(), aCard.cardName().name(), aCard.cardImage().image(), aCard.isOwned(), aCard.isFoiled())));
    }

}
