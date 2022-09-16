package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.domain.card.CardRepository;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetReferer;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class RefreshSetCommandHandler implements CommandHandler<RefreshSetCommand, Void> {

    private static final Logger LOGGER = getLogger(RefreshSetCommandHandler.class);

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

        var setAppliedEvents = setReferer.loadAll()
                .filter(Set::hasBeenReleased)
                .filter(Set::hasCard)
                .filter(Set::isNotDigital)
                .map(set -> Set.add(set.id(),
                        set.code(),
                        set.name(),
                        set.parentSetCode(),
                        set.blockCode(),
                        set.releasedDate(),
                        set.setType(),
                        set.cardCount(),
                        set.icon()));

        var sets = setAppliedEvents.map(AppliedEvent::aggregate);
        LOGGER.info("Saving {} sets ...", sets.size());
        setRepository.save(sets);

        var cardAppliedEvents = sets
                .map(Set::code)
                .flatMap(this::loadCardsFromSet);

        List<Event<?, ?, ?>> events = setAppliedEvents.map(AppliedEvent::event);
        return toCommandResponse(events.appendAll(cardAppliedEvents.map(AppliedEvent::event)));
    }

    private List<AppliedEvent<Card, ? extends Event<CardId, Card, ?>>> loadCardsFromSet(SetCode setCode) {
        List<AppliedEvent<Card, ? extends Event<CardId, Card, ?>>> cards = cardReferer.load(setCode)
                .map(card -> {
                            Option<Card> existingCard = cardRepository.get(card.id());
                            return existingCard.isEmpty()
                                    ? Card.add(card.id(), card.setCode(), card.cardName(), card.cardImage(), card.prices().head())
                                    : card.update(card.id(), card.prices().head(), existingCard.get().isOwned(), existingCard.get().isFoiled());
                        }
                );

        LOGGER.info("Saving {} cards", cards.size());
        cardRepository.save(cards.map(AppliedEvent::aggregate));

        return cards;
    }

}
