package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.card.port.referer.model.CardReferer;
import fr.ignishky.mtgcollection.domain.card.port.referer.CardRefererPort;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScryfallCardRefererPortTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ScryfallProperties properties = new ScryfallProperties("base-url");

    private final CardRefererPort referer = new ScryfallCardRefererPort(restTemplate, properties);

    @Test
    void should_handle_null_response_from_scryfall() {
        // GIVEN
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        // WHEN
        List<? extends CardReferer> cards = referer.load(SNC.code());

        // THEN
        assertThat(cards).isEmpty();
    }

}
