package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.common.InstantFreezeExtension;
import fr.ignishky.mtgcollection.domain.set.SetReferer;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(InstantFreezeExtension.class)
class RefreshSetCommandHandlerTest {

    public static SetAdded aSetAdded = new SetAdded(aSet.id(), aSet.code(), aSet.name(), aSet.icon());
    public static SetAdded anotherSetAdded = new SetAdded(anotherSet.id(), anotherSet.code(), anotherSet.name(), anotherSet.icon());

    private final SetReferer referer = mock(SetReferer.class);
    private final SetRepository repository = mock(SetRepository.class);

    private final RefreshSetCommandHandler commandHandler = new RefreshSetCommandHandler(referer, repository);

    @Test
    void should_load_sets_from_referer_and_save_published_ones_into_repository() {
        // GIVEN
        when(referer.loadAll()).thenReturn(List.of(aSet, anotherSet, aFutureSet));

        // WHEN
        CommandResponse<Void> response = commandHandler.handle(new RefreshSetCommand());

        // THEN
        assertThat(response).isEqualTo(toCommandResponse(aSetAdded, anotherSetAdded));
        verify(repository).save(List.of(aSet, anotherSet));
    }

}
