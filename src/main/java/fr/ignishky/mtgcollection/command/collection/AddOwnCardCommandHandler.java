package fr.ignishky.mtgcollection.command.collection;

import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.exception.NoCardFoundException;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;

@Component
public class AddOwnCardCommandHandler implements CommandHandler<AddOwnCardCommand, Card> {

    private final CardRepository repository;

    public AddOwnCardCommandHandler(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommandResponse<Card> handle(AddOwnCardCommand command) {
        Card card = repository.get(command.cardId()).getOrElseThrow(NoCardFoundException::new);
        AppliedEvent<Card, CardOwned> owned = card.owned(command.isOwnedFoil());

        repository.save(owned.aggregate());
        return toCommandResponse(owned.aggregate(), List.of(owned.event()));
    }

}
