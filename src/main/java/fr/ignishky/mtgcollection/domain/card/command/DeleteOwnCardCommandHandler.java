package fr.ignishky.mtgcollection.domain.card.command;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
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
        var card = cardRepository.get(command.cardId()).getOrElseThrow(NoCardFoundException::new);
        var owned = card.retired();
        cardRepository.save(owned.aggregate());
        List<Event<?, ?, ?>> events = List.of(owned.event());

        if (card.isOwned()) {
            var setUpdated = setRepository.get(card.setCode())
                    .map(set -> set.decrementCardOwned(card.isOwnedFoil()))
                    .get();
            setRepository.update(setUpdated.aggregate());
            events = events.append(setUpdated.event());
        }
        return toCommandResponse(owned.aggregate(), events);
    }

}
