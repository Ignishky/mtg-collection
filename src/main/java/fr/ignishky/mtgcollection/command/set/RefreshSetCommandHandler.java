package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.domain.AppliedEvent;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetReferer;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;

@Slf4j
@Component
public class RefreshSetCommandHandler implements CommandHandler<RefreshSetCommand, Void> {

    private final SetReferer setReferer;
    private final CardReferer cardReferer;
    private final SetRepository setRepository;
    private final CardRepository cardRepository;

    public RefreshSetCommandHandler(SetReferer setReferer, CardReferer cardReferer, SetRepository setRepository, CardRepository cardRepository) {
        this.setReferer = setReferer;
        this.cardReferer = cardReferer;
        this.setRepository = setRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public CommandResponse<Void> handle(RefreshSetCommand command) {

        List<Set> existingSets = setRepository.getAll();

        var setAppliedEvents = setReferer.loadAll()
                .filter(Set::hasBeenReleased)
                .filter(Set::hasCard)
                .filter(set -> !existingSets.contains(set))
                .map(set -> Set.add(set.id(), set.code(), set.name(), set.releasedDate(), set.cardCount(), set.icon()));

        List<Set> sets = setAppliedEvents.map(AppliedEvent::aggregate);
        log.info("Saving {} sets ...", sets.size());
        setRepository.save(sets);

        var cardAppliedEvents = sets.map(Set::code)
                .flatMap(cardReferer::load)
                .map(card -> Card.add(card.id(), card.setCode(), card.cardName(), card.cardImage()));
        log.info("Saving {} cards", cardAppliedEvents.size());
        cardRepository.save(cardAppliedEvents.map(AppliedEvent::aggregate));

        List<Event<?, ?, ?>> events = List.ofAll(setAppliedEvents.map(AppliedEvent::event));
        return toCommandResponse(events.appendAll(cardAppliedEvents.map(AppliedEvent::event)));
    }

}
