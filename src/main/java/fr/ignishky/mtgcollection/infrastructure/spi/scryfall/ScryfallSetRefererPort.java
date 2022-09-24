package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.set.referer.SetReferer;
import fr.ignishky.mtgcollection.domain.set.referer.SetRefererPort;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
class ScryfallSetRefererPort implements SetRefererPort {

    private final RestTemplate restTemplate;
    private final ScryfallProperties scryfallProperties;

    ScryfallSetRefererPort(RestTemplate restTemplate, ScryfallProperties scryfallProperties) {
        this.restTemplate = restTemplate;
        this.scryfallProperties = scryfallProperties;
    }

    @Override
    public List<? extends SetReferer> loadAll() {
        return Option.of(restTemplate.getForObject("%s/sets".formatted(scryfallProperties.baseUrl()), SetScryfall.class))
                .map(SetScryfall::data)
                .getOrElse(List.empty());
    }

}
