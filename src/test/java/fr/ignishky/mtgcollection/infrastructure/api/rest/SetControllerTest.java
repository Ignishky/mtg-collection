package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static fr.ignishky.mtgcollection.common.ApiFixtures.aRestSet;
import static fr.ignishky.mtgcollection.common.ApiFixtures.anotherRestSet;
import static fr.ignishky.mtgcollection.common.DomainFixtures.aSet;
import static fr.ignishky.mtgcollection.common.DomainFixtures.anotherSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        List<SetRest> sets = controller.getAll();

        // THEN
        assertThat(sets).containsOnly(aRestSet, anotherRestSet);
    }
}