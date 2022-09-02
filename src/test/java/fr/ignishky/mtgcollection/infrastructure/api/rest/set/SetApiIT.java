package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfall;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
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
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument.toCardDocument;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument.toSetDocument;
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
    }

    @Test
    void should_load_sets_from_scryfall_and_save_them_into_mongo() throws Exception {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg.test/sets", SetScryfall.class)).thenReturn(aScryfallSets);
        when(restTemplate.getForObject("http://scryfall.mtg.test/cards/search?order=set&q=e:%s&unique=prints".formatted(StreetOfNewCapenna.code()), CardScryfall.class))
                .thenReturn(aScryfallCards);
        when(restTemplate.getForObject("http://scryfall.mtg.test/cards/search?order=set&q=e:%s&unique=prints".formatted(Kaldheim.code()), CardScryfall.class))
                .thenReturn(anotherScryfallCards);
        when(restTemplate.getForObject("https://scryfall.mtg.test/page:2", CardScryfall.class)).thenReturn(anotherScryfallCards2);
        when(restTemplate.getForObject("http://scryfall.mtg.test/cards/search?order=set&q=e:%s&unique=prints".formatted(aFailedSet.code()), CardScryfall.class))
                .thenThrow(RestClientException.class);

        // WHEN
        ResultActions resultActions = mvc.perform(put("/sets"));

        // THEN
        resultActions.andExpect(status().isNoContent());
        assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toSetDocument(StreetOfNewCapenna), toSetDocument(Kaldheim), toSetDocument(aFailedSet));
        assertThat(mongoTemplate.findAll(CardDocument.class))
                .containsOnly(toCardDocument(aCard), toCardDocument(anotherCard), toCardDocument(anExtraCard), toCardDocument(anotherCard2));

        var eventDocuments = mongoTemplate.findAll(EventDocument.class);
        assertThat(eventDocuments).hasSize(7);
        assertEvent(eventDocuments.get(0), aFailedSet.id(), "SetAdded", "{\"code\":\"fail\",\"name\":\"FAILED SET\",\"releaseDate\":\"2021-12-01\",\"setType\":\"expansion\",\"cardCount\":1,\"icon\":\"icon5\"}");
        assertEvent(eventDocuments.get(1), StreetOfNewCapenna.id(), "SetAdded", "{\"code\":\"snc\",\"name\":\"Streets of New Capenna\",\"releaseDate\":\"2022-04-29\",\"setType\":\"expansion\",\"cardCount\":467,\"icon\":\"https://scryfall.mtgc.test/sets/snc.svg\"}");
        assertEvent(eventDocuments.get(2), Kaldheim.id(), "SetAdded", "{\"code\":\"khm\",\"name\":\"Kaldheim\",\"releaseDate\":\"2020-04-24\",\"setType\":\"expansion\",\"cardCount\":390,\"icon\":\"https://scryfall.mtgc.test/sets/khm.svg\"}");
        assertEvent(eventDocuments.get(3), aCard.id(), "CardAdded", "{\"name\":\"a-card-name\",\"setCode\":\"snc\",\"image\":\"a-card-image\"}");
        assertEvent(eventDocuments.get(4), anExtraCard.id(), "CardAdded", "{\"name\":\"an-extra-card-name\",\"setCode\":\"snc\",\"image\":\"an-extra-card-image\"}");
        assertEvent(eventDocuments.get(5), anotherCard.id(), "CardAdded", "{\"name\":\"another-card-name\",\"setCode\":\"khm\",\"image\":\"another-card-image\"}");
        assertEvent(eventDocuments.get(6), anotherCard2.id(), "CardAdded", "{\"name\":\"another-card-name2\",\"setCode\":\"khm\",\"image\":\"another-card-image2\"}");
    }

    @Test
    void should_return_not_found_when_set_code_is_unknown() throws Exception {
        // GIVEN
        mongoTemplate.insertAll(List.of(toSetDocument(StreetOfNewCapenna), toCardDocument(anotherCard)).asJava());

        // WHEN
        ResultActions resultActions = mvc.perform(get("/sets/%s".formatted(StreetOfNewCapenna.code())));

        // THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void should_return_all_cards_for_a_given_set_from_repository() throws Exception {
        // GIVEN
        mongoTemplate.insertAll(List.of(toSetDocument(StreetOfNewCapenna), toCardDocument(aCard), toCardDocument(anotherCard), toCardDocument(anExtraCard)).asJava());

        // WHEN
        ResultActions resultActions = mvc.perform(get("/sets/%s".formatted(StreetOfNewCapenna.code())));

        // THEN
        resultActions.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/set/setResponse.json"), true)
        );
    }

}
