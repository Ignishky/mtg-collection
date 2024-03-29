package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.card.port.referer.model.CardReferer;
import fr.ignishky.mtgcollection.domain.card.port.referer.CardRefererPort;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.ScryfallCard;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.ScryfallCardData;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class ScryfallCardRefererPort implements CardRefererPort {

    private static final Logger LOGGER = getLogger(ScryfallCardRefererPort.class);

    private final RestTemplate restTemplate;
    private final ScryfallProperties scryfallProperties;

    public ScryfallCardRefererPort(RestTemplate restTemplate, ScryfallProperties scryfallProperties) {
        this.restTemplate = restTemplate;
        this.scryfallProperties = scryfallProperties;
    }

    @Override
    public List<? extends CardReferer> load(SetCode setCode) {
        LOGGER.info("Loading cards from {} ...", setCode.value());
        String url = "%s/cards/search?order=set&q=e:%s&unique=prints".formatted(scryfallProperties.baseUrl(), setCode.value());
        List<ScryfallCard> cards = List.empty();
        while (url != null) {
            ScryfallCard scryfallCard;
            try {
                scryfallCard = restTemplate.getForObject(url.replace("%3A", ":"), ScryfallCard.class);
            } catch (RestClientException e) {
                LOGGER.warn("Impossible to get cards from set `{}`", setCode, e);
                break;
            }
            if (scryfallCard != null) {
                cards = cards.append(scryfallCard);
                url = scryfallCard.nextPage();
            } else {
                url = null;
            }
        }
        return cards
                .flatMap(ScryfallCard::data)
                .filter(ScryfallCardData::isNotDigital)
                .filter(ScryfallCardData::hasImage);
    }

}
