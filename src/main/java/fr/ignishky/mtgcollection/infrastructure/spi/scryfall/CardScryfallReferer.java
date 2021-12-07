package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Repository
public class CardScryfallReferer implements CardReferer {

    private final RestTemplate restTemplate;
    private final ScryfallProperties scryfallProperties;

    public CardScryfallReferer(RestTemplate restTemplate, ScryfallProperties scryfallProperties) {
        this.restTemplate = restTemplate;
        this.scryfallProperties = scryfallProperties;
    }

    @Override
    public List<Card> load(SetCode setCode) {
        log.info("Loading cards from {} ...", setCode.value());
        String url = scryfallProperties.baseUrl() + "/cards/search?order=set&q=e:" + setCode.value() + "&unique=prints";
        CardScryfall cardScryfall = restTemplate.getForObject(url, CardScryfall.class);
        return Option.of(cardScryfall)
                .map(CardScryfall::data)
                .getOrElse(List.empty())
                .map(CardScryfall.ScryfallData::toCard)
                .filter(CardScryfallReferer::withImage);
    }

    private static boolean withImage(Card card) {
        return card.cardImage().image() != null;
    }

}
