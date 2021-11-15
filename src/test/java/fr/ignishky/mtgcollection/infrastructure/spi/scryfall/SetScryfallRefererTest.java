package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetReferer;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.common.SpiFixtures.aScryfallSets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SetScryfallRefererTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ScryfallProperties properties = new ScryfallProperties("base-url");

    private final SetReferer referer = new SetScryfallReferer(restTemplate, properties);

    @Test
    void should_load_sets_from_scryfall() {
        // GIVEN
        when(restTemplate.getForObject("base-url/sets", SetScryfall.class)).thenReturn(aScryfallSets);

        // WHEN
        List<Set> actual = referer.loadAll();

        // THEN
        assertThat(actual).containsOnly(aFutureSet, aSet, anotherSet);
    }

}
