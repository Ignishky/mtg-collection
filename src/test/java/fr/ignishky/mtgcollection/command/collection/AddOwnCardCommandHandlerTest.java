package fr.ignishky.mtgcollection.command.collection;

import fr.ignishky.mtgcollection.common.InstantFreezeExtension;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static fr.ignishky.mtgcollection.common.DomainFixtures.aCard;
import static fr.ignishky.mtgcollection.common.DomainFixtures.aOwnedCard;
import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(InstantFreezeExtension.class)
class AddOwnCardCommandHandlerTest {

    private final CardRepository repository = mock(CardRepository.class);

    private final AddOwnCardCommandHandler commandHandler = new AddOwnCardCommandHandler(repository);

    @Test
    void should_fail_to_owned_missing_card() {
        // GIVEN
        when(repository.get(aCard.id())).thenReturn(Option.none());

        // WHEN & THEN
        assertThatThrownBy(() -> commandHandler.handle(new AddOwnCardCommand(aCard.id(), false))).isInstanceOf(NoCardFoundException.class);
        verify(repository, never()).save(any(Card.class));
    }

    @Test
    void should_add_card_to_collection() {
        // GIVEN
        when(repository.get(aCard.id())).thenReturn(Option.of(aCard));

        // WHEN
        CommandResponse<Card> commandResponse = commandHandler.handle(new AddOwnCardCommand(aCard.id(), true));

        // THEN
        verify(repository).save(aOwnedCard);
        assertThat(commandResponse).isEqualTo(toCommandResponse(aOwnedCard, List.of(new CardOwned(aCard.id(), true, true))));
    }

}
