package fr.ignishky.mtgcollection.domain.set.command;

import fr.ignishky.mtgcollection.domain.card.*;
import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardUpdated;
import fr.ignishky.mtgcollection.domain.card.referer.CardReferer;
import fr.ignishky.mtgcollection.domain.card.referer.CardRefererPort;
import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.domain.set.referer.SetReferer;
import fr.ignishky.mtgcollection.domain.set.referer.SetRefererPort;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;
import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;
import static java.time.LocalDate.now;
import static java.util.Locale.ROOT;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class RefreshSetCommandHandler implements CommandHandler<RefreshSetCommand, Void> {

    private static final Logger LOGGER = getLogger(RefreshSetCommandHandler.class);

    private final SetRefererPort setRefererPort;
    private final CardRefererPort cardRefererPort;
    private final SetRepository setRepository;
    private final CardRepository cardRepository;

    public RefreshSetCommandHandler(SetRefererPort setRefererPort, CardRefererPort cardRefererPort, SetRepository setRepository,
                                    CardRepository cardRepository) {
        this.setRefererPort = setRefererPort;
        this.cardRefererPort = cardRefererPort;
        this.setRepository = setRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public CommandResponse<Void> handle(RefreshSetCommand command) {

        var refererSets = setRefererPort.loadAll()
                .filter(SetReferer::hasBeenReleased)
                .filter(SetReferer::hasCard)
                .filter(SetReferer::isNotDigital);
        var newSets = saveOnlyNewSets(refererSets);

        var cardAppliedEvents = refererSets
                .map(SetReferer::code)
                .flatMap(this::loadCardsFromSet);

        List<Event<?, ?, ?>> events = newSets.map(AppliedEvent::event);
        return toCommandResponse(events.appendAll(cardAppliedEvents.map(AppliedEvent::event)));
    }

    private List<AppliedEvent<Set, SetAdded>> saveOnlyNewSets(List<? extends SetReferer> refererSets) {
        var existingSetCodes = setRepository.getAll().map(Set::code);
        var newSets = refererSets
                .filter(refererSet -> !existingSetCodes.contains(new SetCode(refererSet.code())))
                .map(RefreshSetCommandHandler::toSetAdded);

        LOGGER.info("Saving {} new sets ...", newSets.size());
        setRepository.save(newSets.map(AppliedEvent::aggregate));
        return newSets;
    }

    private static AppliedEvent<Set, SetAdded> toSetAdded(SetReferer setReferer) {
        return Set.add(
                toSetId(setReferer.id()),
                new SetCode(setReferer.code()),
                new SetName(setReferer.name()),
                Option.of(setReferer.parentSetCode()).map(SetCode::new),
                Option.of(setReferer.blockCode()).map(SetCode::new),
                setReferer.releasedAt(),
                SetType.valueOf(setReferer.setType().toUpperCase(ROOT)),
                setReferer.cardCount(),
                new SetIcon(setReferer.icon())
        );
    }

    private List<? extends AppliedEvent<Card, ? extends Event<CardId, Card, ?>>> loadCardsFromSet(String setCode) {
        var cards = cardRefererPort.load(new SetCode(setCode))
                .map(this::getCardAppliedEvent)
                .filter(Objects::nonNull);

        LOGGER.info("Saving {} cards", cards.size());
        cardRepository.save(cards.map(AppliedEvent::aggregate));

        return cards;
    }

    private AppliedEvent<Card, ? extends Event<CardId, Card, ?>> getCardAppliedEvent(CardReferer cardReferer) {
        Option<Card> existingCard = cardRepository.get(new CardId(cardReferer.id()));

        AppliedEvent<Card, ? extends Event<CardId, Card, ?>> event = null;
        if (existingCard.isEmpty()) {
            event = getAddedAppliedEvent(cardReferer);
        } else if (cardReferer.hasPrice() && existingCard.get().lastUpdate().isBefore(now())) {
            event = getUpdatedAppliedEvent(cardReferer, existingCard.get());
        }
        return event;
    }

    private static AppliedEvent<Card, CardAdded> getAddedAppliedEvent(CardReferer cardReferer) {
        return Card.add(
                new CardId(cardReferer.id()),
                new SetCode(cardReferer.set()),
                new CardName(cardReferer.name()),
                new CardImage(cardReferer.images() != null
                        ? cardReferer.images().normal()
                        : cardReferer.cardFaces().head().imageUris().normal()
                ),
                cardReferer.finishes().map(Finish::fromValue),
                new Price(cardReferer.prices().euro(), cardReferer.prices().euroFoil())
        );
    }

    private static AppliedEvent<Card, CardUpdated> getUpdatedAppliedEvent(CardReferer cardReferer, Card existingCard) {
        return existingCard.update(
                new CardId(cardReferer.id()),
                new Price(cardReferer.prices().euro(), cardReferer.prices().euroFoil())
        );
    }

}
