package fr.ignishky.mtgcollection.command.collection;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
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
        AppliedEvent<Card, CardOwned> cardOwned = card.owned(command.isOwnedFoil());
        cardRepository.save(cardOwned.aggregate());

        List<Event<?, ?, ?>> events = List.of(cardOwned.event());
        if (!card.isOwned()) {
            var setUpdated = setRepository.get(card.setCode())
                    .map(Set::incrementCardOwned)
                    .get();
            setRepository.update(setUpdated.aggregate());
            events = events.append(setUpdated.event());
        }
        return toCommandResponse(cardOwned.aggregate(), events);
    }

}
