package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse;
import fr.ignishky.mtgcollection.query.set.GetCardsQuery;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse.CardSummary.toCardSummary;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse.SetSummary.toSetSummary;
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
        // WHEN
        controller.loadAll();

        // THEN
        verify(commandBus).dispatch(new RefreshSetCommand());
    }

    @Test
    void should_return_all_set_from_query() {
        // GIVEN
        when(queryBus.dispatch(new GetSetsQuery())).thenReturn(List.of(aSet, anotherSet));

        // WHEN
        ResponseEntity<SetsResponse> response = controller.getAll();

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(new SetsResponse(List.of(toSetSummary(aSet), toSetSummary(anotherSet))), OK));
    }

    @Test
    void should_return_not_found_when_query_return_no_card() {
        // GIVEN
        when(queryBus.dispatch(new GetCardsQuery(aSet.code()))).thenReturn(List.empty());

        // WHEN
        ResponseEntity<SetResponse> response = controller.getCards(aSet.code().value());

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(NOT_FOUND));
    }

    @Test
    void should_return_cards_from_query() {
        // GIVEN
        when(queryBus.dispatch(new GetCardsQuery(aSet.code()))).thenReturn(List.of(aCard, anExtraCard));

        // WHEN
        ResponseEntity<SetResponse> response = controller.getCards(aSet.code().value());

        // THEN
        assertThat(response).isEqualTo(new ResponseEntity<>(new SetResponse(List.of(toCardSummary(aCard), toCardSummary(anExtraCard))), OK));
    }

}
