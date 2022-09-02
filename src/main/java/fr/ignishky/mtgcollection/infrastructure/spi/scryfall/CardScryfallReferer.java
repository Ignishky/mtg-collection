package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class CardScryfallReferer implements CardReferer {

    private static final Logger LOGGER = getLogger(CardScryfallReferer.class);

    private final RestTemplate restTemplate;
    private final ScryfallProperties scryfallProperties;

    public CardScryfallReferer(RestTemplate restTemplate, ScryfallProperties scryfallProperties) {
        this.restTemplate = restTemplate;
        this.scryfallProperties = scryfallProperties;
    }

    @Override
    public List<Card> load(SetCode setCode) {
        LOGGER.info("Loading cards from {} ...", setCode.value());
        String url = "%s/cards/search?order=set&q=e:%s&unique=prints".formatted(scryfallProperties.baseUrl(), setCode.value());
        List<CardScryfall> cards = List.empty();
        while (url != null) {
            CardScryfall cardScryfall;
            try {
                cardScryfall = restTemplate.getForObject(url.replace("%3A", ":"), CardScryfall.class);
            } catch (RestClientException e) {
                LOGGER.warn("Impossible to get cards from set `{}`", setCode, e);
                break;
            }
            if (cardScryfall != null) {
                cards = cards.append(cardScryfall);
                url = cardScryfall.next_page();
            } else {
                url = null;
            }
        }
        return cards
                .flatMap(CardScryfall::data)
                .map(CardScryfall.ScryfallData::toCard)
                .filter(CardScryfallReferer::hasImage);
    }

    private static boolean hasImage(Card card) {
        return card.cardImage().image() != null;
    }

}
