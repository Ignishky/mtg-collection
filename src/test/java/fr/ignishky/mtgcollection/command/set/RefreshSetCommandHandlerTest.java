package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.common.InstantFreezeExtension;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
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

    private static final SetAdded aSetAdded = new SetAdded(aSet.id(), aSet.code(), aSet.name(), aSet.releasedDate(), aSet.cardCount(), aSet.icon());
    private static final CardAdded aCardAdded = new CardAdded(aCard.id(), aSet.code(), aCard.cardName(), aCard.cardImage());
    private static final CardAdded anExtraCardAdded = new CardAdded(anExtraCard.id(), aSet.code(), anExtraCard.cardName(), anExtraCard.cardImage());

    private final SetReferer setReferer = mock(SetReferer.class);
    private final CardReferer cardReferer = mock(CardReferer.class);
    private final SetRepository setRepository = mock(SetRepository.class);
    private final CardRepository cardRepository = mock(CardRepository.class);

    private final RefreshSetCommandHandler commandHandler = new RefreshSetCommandHandler(setReferer, cardReferer, setRepository, cardRepository);

    @Test
    void should_load_sets_and_cards_from_referer_and_save_published_ones_into_repository() {
        // GIVEN
        when(setReferer.loadAll()).thenReturn(List.of(aSet, anotherSet, aFutureSet));
        when(cardReferer.load(aSet.code())).thenReturn(List.of(aCard, anExtraCard));
        when(setRepository.getAll()).thenReturn(List.of(anotherSet));

        // WHEN
        CommandResponse<Void> response = commandHandler.handle(new RefreshSetCommand());

        // THEN
        assertThat(response).isEqualTo(toCommandResponse(List.of(aSetAdded, aCardAdded, anExtraCardAdded)));
        verify(setRepository).save(List.of(aSet));
        verify(cardRepository).save(List.of(aCard, anExtraCard));
    }

}
