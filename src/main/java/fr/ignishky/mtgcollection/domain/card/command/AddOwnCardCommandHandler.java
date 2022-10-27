package fr.ignishky.mtgcollection.domain.card.command;

import fr.ignishky.mtgcollection.domain.card.model.Card;
import fr.ignishky.mtgcollection.domain.card.port.repository.CardRepository;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.domain.set.port.repository.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;

@Component
public class AddOwnCardCommandHandler implements CommandHandler<AddOwnCardCommand, Card> {

    private final SetRepository setRepository;
    private final CardRepository cardRepository;

    public AddOwnCardCommandHandler(SetRepository setRepository, CardRepository cardRepository) {
        this.setRepository = setRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public CommandResponse<Card> handle(AddOwnCardCommand command) {
        var card = cardRepository.get(command.cardId()).getOrElseThrow(NoCardFoundException::new);
        var cardOwned = card.owned(command.isOwnedFoil());
        cardRepository.save(cardOwned.aggregate());

        List<Event<?, ?, ?>> events = List.of(cardOwned.event());
        if (!card.isOwned()) {
            var setUpdated = setRepository.get(card.setCode())
                    .map(set -> set.incrementCardOwned(command.isOwnedFoil()))
                    .get();
            setRepository.update(setUpdated.aggregate());
            events = events.append(setUpdated.event());
        }
        return toCommandResponse(cardOwned.aggregate(), events);
    }

}
