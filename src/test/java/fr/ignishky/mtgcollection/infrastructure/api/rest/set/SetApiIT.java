package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.Price;
import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardUpdated;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.ScryfallCard;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.ScryfallSet;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.TestUtils.assertEvent;
import static fr.ignishky.mtgcollection.TestUtils.readFile;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.KHM;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.SetApi.SETS_PATH;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toDocument;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SetApiIT {

    private static final String SCRYFALL_SETS_URL = "http://scryfall.mtg.test/sets";
    private static final String SCRYFALL_SET_DETAIL_URL_PATTERN = "http://scryfall.mtg.test/cards/search?order=set&q=e:%s&unique=prints";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(SetDocument.class);
        mongoTemplate.dropCollection(CardDocument.class);
        mongoTemplate.dropCollection(EventDocument.class);
    }

    @Nested
    class PutSets {

        @Test
        void should_ignore_update_when_referer_sent_future_set() throws Exception {
            // GIVEN
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("futureSet.json"));

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(SetDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        }

        @Test
        void should_ignore_update_when_referer_sent_digital_set() throws Exception {
            // GIVEN
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("digitalSet.json"));

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(SetDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        }

        @Test
        void should_save_set_details_when_referer_failed_to_send_cards_list() throws Exception {
            // GIVEN
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("snc.json"));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), ScryfallCard.class))
                    .thenThrow(RestClientException.class);

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(1);
            assertEvent(eventDocuments.get(0), SNC.id(), SetAdded.class, readEventSet("sncAdded.json"));

            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC));
            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        }

        @Test
        void should_ignore_update_when_referer_sent_card_with_no_price() throws Exception {
            // GIVEN
            save(ledgerShredder);
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("snc.json"));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), ScryfallCard.class))
                    .thenReturn(readCardScryfall("withoutPrices.json"));

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC));
            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredder));
        }

        @Test
        void should_load_sets_from_scryfall_and_save_them_into_mongo() throws Exception {
            // GIVEN
            save(ledgerShredder.withLastUpdate(now().minusDays(1L)));
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("sets.json"));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), ScryfallCard.class))
                    .thenReturn(readCardScryfall("singleSncPage.json"));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(KHM.code()), ScryfallCard.class))
                    .thenReturn(readCardScryfall("firstKhmPage.json"));
            when(restTemplate.getForObject("https://scryfall.mtg.test/page:2", ScryfallCard.class))
                    .thenReturn(readCardScryfall("lastKhmPage.json"));

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(5);
            assertEvent(eventDocuments.get(0), KHM.id(), SetAdded.class, readEventSet("khmAdded.json"));
            assertEvent(eventDocuments.get(1), ledgerShredder.id(), CardUpdated.class, readEventCard("ledgerShredderUpdated.json"));
            assertEvent(eventDocuments.get(2), depopulate.id(), CardAdded.class, readEventCard("depopulateAdded.json"));
            assertEvent(eventDocuments.get(3), vorinclex.id(), CardAdded.class, readEventCard("vorinclexAdded.json"));
            assertEvent(eventDocuments.get(4), esika.id(), CardAdded.class, readEventCard("esikaAdded.json"));

            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC), toDocument(KHM));
            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(
                    toDocument(ledgerShredder.withPrices(new Price(27.24, 30.16))),
                    toDocument(depopulate),
                    toDocument(vorinclex),
                    toDocument(esika)
            );
        }

        @Test
        void should_ignore_update_when_already_done_same_day() throws Exception {
            // GIVEN
            save(ledgerShredder.withLastUpdate(now().minusDays(1L)));
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, ScryfallSet.class)).thenReturn(readSetScryfall("snc.json"));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), ScryfallCard.class))
                    .thenReturn(readCardScryfall("singleSncPage.json"));

            // WHEN
            var firstCall = mvc.perform(put(SETS_PATH));
            var secondCall = mvc.perform(put(SETS_PATH));

            // THEN
            firstCall.andExpect(status().isNoContent());
            secondCall.andExpect(status().isNoContent());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(2);
            assertEvent(eventDocuments.get(0), ledgerShredder.id(), CardUpdated.class, readEventCard("ledgerShredderUpdated.json"));
            assertEvent(eventDocuments.get(1), depopulate.id(), CardAdded.class, readEventCard("depopulateAdded.json"));

            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC));
            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(
                    toDocument(ledgerShredder.withPrices(new Price(27.24, 30.16))),
                    toDocument(depopulate)
            );
        }

        private ScryfallSet readSetScryfall(String fileName) throws JsonProcessingException {
            return objectMapper.readValue(readFile("/set/scryfall/%s".formatted(fileName)), ScryfallSet.class);
        }

        private ScryfallCard readCardScryfall(String fileName) throws JsonProcessingException {
            return objectMapper.readValue(readFile("/card/scryfall/%s".formatted(fileName)), ScryfallCard.class);
        }

        private static String readEventSet(String fileName) {
            return readFile("/set/event/%s".formatted(fileName));
        }

        private static String readEventCard(String fileName) {
            return readFile("/card/event/%s".formatted(fileName));
        }

    }

    @Nested
    class GetSets {

        @Test
        void should_return_not_found_when_set_code_is_unknown() throws Exception {
            // GIVEN
            save(vorinclex);

            // WHEN
            var resultActions = mvc.perform(get(SETS_PATH + "/UNKNOWN"));

            // THEN
            resultActions.andExpect(status().isNotFound());
        }

        @Test
        void should_return_all_cards_for_a_given_set_from_repository() throws Exception {
            // GIVEN
            save(ledgerShredder, vorinclex, depopulate.withOwned(true));

            // WHEN
            var resultActions = mvc.perform(get("%s/%s".formatted(SETS_PATH, SNC.code())));

            // THEN
            resultActions.andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON),
                    content().json(readFile("/set/setResponse.json"), true)
            );
        }

    }

    private void save(Card... cards) {
        List<Record> documents = List.of(cards).map(MongoDocumentMapper::toDocument);
        mongoTemplate.insertAll(documents.append(toDocument(SNC)).asJava());
    }

}
