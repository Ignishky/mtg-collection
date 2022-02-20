package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.query.set.GetCardsQuery;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.CardResponse.toCardResponse;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.SetResponse.toSetResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class SetControllerTest {

    private final CommandBus commandBus = mock(CommandBus.class);
    private final QueryBus queryBus = mock(QueryBus.class);

    private final SetApi controller = new SetController(commandBus, queryBus);

    @Test
    void when_loading_sets_should_generate_a_RefreshSetCommand() {
        // GIVEN
        // WHEN
        controller.loadAll();

        // THEN
        verify(commandBus).dispatch(new RefreshSetCommand());
    }

    @Test
    void should_return_all_set_from_query() {
        // GIVEN
        GetSetsQuery query = new GetSetsQuery();
        when(queryBus.dispatch(query)).thenReturn(List.of(aSet, anotherSet));

        // WHEN
        ResponseEntity<List<SetResponse>> response = controller.getAll();

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(List.of(toSetResponse(aSet), toSetResponse(anotherSet)), OK));
    }

    @Test
    void should_return_not_found_when_query_return_no_card() {
        // GIVEN
        GetCardsQuery query = new GetCardsQuery(aSet.code());
        when(queryBus.dispatch(query)).thenReturn(List.empty());

        // WHEN
        ResponseEntity<List<CardResponse>> response = controller.getCards(aSet.code().value());

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(NOT_FOUND));
    }

    @Test
    void should_return_cards_from_query() {
        // GIVEN
        GetCardsQuery query = new GetCardsQuery(aSet.code());
        when(queryBus.dispatch(query)).thenReturn(List.of(aCard, anExtraCard));

        // WHEN
        ResponseEntity<List<CardResponse>> response = controller.getCards(aSet.code().value());

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(List.of(toCardResponse(aCard), toCardResponse(anExtraCard)), OK));
    }

}