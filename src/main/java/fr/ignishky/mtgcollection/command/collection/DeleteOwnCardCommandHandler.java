package fr.ignishky.mtgcollection.command.collection;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.event.CardRetired;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;

@Component
public class DeleteOwnCardCommandHandler implements CommandHandler<DeleteOwnCardCommand, Card> {

    private final SetRepository setRepository;
    private final CardRepository cardRepository;

    public DeleteOwnCardCommandHandler(SetRepository setRepository, CardRepository cardRepository) {
        this.setRepository = setRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public CommandResponse<Card> handle(DeleteOwnCardCommand command) {
        Card card = cardRepository.get(command.cardId()).getOrElseThrow(NoCardFoundException::new);
        AppliedEvent<Card, CardRetired> owned = card.retired();
        cardRepository.save(owned.aggregate());
        List<Event<?, ?, ?>> events = List.of(owned.event());

        if (card.isOwned()) {
            var setUpdated = setRepository.get(card.setCode())
                    .map(Set::decrementCardOwned)
                    .get();
            setRepository.update(setUpdated.aggregate());
            events = events.append(setUpdated.event());
        }
        return toCommandResponse(owned.aggregate(), events);
    }

}
