package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.configuration.ScryfallProperties;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardReferer;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.common.SpiFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class wCardScryfallRefererTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ScryfallProperties properties = new ScryfallProperties("base-url");

    private final CardReferer referer = new CardScryfallReferer(restTemplate, properties);

    @Test
    void should_handle_null_response_from_scryfall() {
        // GIVEN
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        // WHEN
        List<Card> cards = referer.load(aSet.code());

        // THEN
        assertThat(cards).isEmpty();
    }

    @Test
    void should_load_cards_of_a_set_from_scryfall() {
        // GIVEN
        when(restTemplate.getForObject("base-url/cards/search?order=set&q=e:a-set-code&unique=prints", CardScryfall.class))
                .thenReturn(aScryfallCards);

        // WHEN
        List<Card> cards = referer.load(aSet.code());

        // THEN
        assertThat(cards).containsOnly(aCard, anExtraCard);
    }

    @Test
    void should_load_cards_of_a_set_from_multiple_scryfall_pages() {
        // GIVEN
        when(restTemplate.getForObject("base-url/cards/search?order=set&q=e:another-set-code&unique=prints", CardScryfall.class))
                .thenReturn(anotherScryfallCards);
        when(restTemplate.getForObject("https://scryfall.mtg.test/page:2", CardScryfall.class)).thenReturn(anotherScryfallCards2);

        // WHEN
        List<Card> cards = referer.load(anotherSet.code());

        // THEN
        assertThat(cards).containsOnly(anotherCard, anotherCard2);
    }
}
