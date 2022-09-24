package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.event.CardAdded;
import fr.ignishky.mtgcollection.domain.card.event.CardUpdated;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.TestUtils.assertEvent;
import static fr.ignishky.mtgcollection.TestUtils.readFile;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.ScryfallFixtures.*;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.KHM;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.set.SetApi.SETS_PATH;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toDocument;
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
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, SetScryfall.class))
                    .thenReturn(new SetScryfall(List.of(getSetData(aFutureSet))));

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
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, SetScryfall.class))
                    .thenReturn(new SetScryfall(List.of(getSetData(aDigitalSet))));

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
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, SetScryfall.class))
                    .thenReturn(new SetScryfall(List.of(getSetData(aFailedSet))));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(aFailedSet.code()), CardScryfall.class))
                    .thenThrow(RestClientException.class);

            // WHEN
            var resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(1);
            assertEvent(eventDocuments.get(0), aFailedSet.id(), SetAdded.class, readFile("/set/setFailedAdded.json"));

            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(aFailedSet));
            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        }

        @Test
        void should_ignore_update_when_referer_sent_card_with_no_price() throws Exception {
            // GIVEN
            save(ledgerShredder);
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, SetScryfall.class))
                    .thenReturn(new SetScryfall(List.of(getSetData(SNC))));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), CardScryfall.class))
                    .thenReturn(ledgerShredderWithoutPrice);

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
            save(ledgerShredderOwnedFoiled);
            when(restTemplate.getForObject(SCRYFALL_SETS_URL, SetScryfall.class))
                    .thenReturn(new SetScryfall(List.of(getSetData(SNC), getSetData(KHM))));
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(SNC.code()), CardScryfall.class)).thenReturn(singleSNCPage);
            when(restTemplate.getForObject(SCRYFALL_SET_DETAIL_URL_PATTERN.formatted(KHM.code()), CardScryfall.class)).thenReturn(firstKHMPage);
            when(restTemplate.getForObject("https://scryfall.mtg.test/page:2", CardScryfall.class)).thenReturn(lastKHMPage);

            // WHEN
            ResultActions resultActions = mvc.perform(put(SETS_PATH));

            // THEN
            resultActions.andExpect(status().isNoContent());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(5);
            assertEvent(eventDocuments.get(0), KHM.id(), SetAdded.class, readFile("/set/setKhmAdded.json"));
            assertEvent(eventDocuments.get(1), ledgerShredder.id(), CardUpdated.class, readFile("/set/cardUpdateEvent.json"));
            assertEvent(eventDocuments.get(2), depopulate.id(), CardAdded.class, readFile("/set/cardDepopulateAdded.json"));
            assertEvent(eventDocuments.get(3), vorinclex.id(), CardAdded.class, readFile("/set/cardVorinclexAdded.json"));
            assertEvent(eventDocuments.get(4), esika.id(), CardAdded.class, readFile("/set/cardEsikaAdded.json"));

            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC), toDocument(KHM));
            assertThat(mongoTemplate.findAll(CardDocument.class))
                    .containsOnly(toDocument(anUpdatedOwnedCard), toDocument(vorinclex), toDocument(depopulate), toDocument(esika));
        }

    }

    @Nested
    class GetSets {

        @Test
        void should_return_not_found_when_set_code_is_unknown() throws Exception {
            // GIVEN
            save(vorinclex);

            // WHEN
            ResultActions resultActions = mvc.perform(get(SETS_PATH + "/UNKNOWN"));

            // THEN
            resultActions.andExpect(status().isNotFound());
        }

        @Test
        void should_return_all_cards_for_a_given_set_from_repository() throws Exception {
            // GIVEN
            save(ledgerShredderOwnedFoiled, vorinclex, depopulate);

            // WHEN
            ResultActions resultActions = mvc.perform(get("%s/%s".formatted(SETS_PATH, SNC.code())));

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
