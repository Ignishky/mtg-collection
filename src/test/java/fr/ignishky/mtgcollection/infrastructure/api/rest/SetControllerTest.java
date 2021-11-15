package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.command.set.RefreshSetCommand;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandBus;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SetControllerTest {

    private final CommandBus commandBus = mock(CommandBus.class);

    private final SetController controller = new SetController(commandBus);

    @Test
    void should_delegate_to_setLoader() {
        // GIVEN
        // WHEN
        controller.loadAll();

        // THEN
        verify(commandBus).dispatch(new RefreshSetCommand());
    }
}