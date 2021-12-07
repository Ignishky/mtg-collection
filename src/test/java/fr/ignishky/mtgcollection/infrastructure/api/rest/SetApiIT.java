package fr.ignishky.mtgcollection.infrastructure.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
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
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.common.ApiFixtures.aRestSet;
import static fr.ignishky.mtgcollection.common.ApiFixtures.anotherRestSet;
import static fr.ignishky.mtgcollection.common.SpiFixtures.*;
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
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(SetDocument.class);
    }

    @Test
    void should_load_sets_from_scryfall_and_save_them_into_mongo() throws Exception {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", SetScryfall.class)).thenReturn(aScryfallSets);
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/cards/search?order=set&q=e:a-set-code&unique=prints", CardScryfall.class)).thenReturn(aScryfallCards);
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/cards/search?order=set&q=e:another-set-code&unique=prints", CardScryfall.class)).thenReturn(anotherScryfallCards);

        // WHEN
        ResultActions resultActions = mvc.perform(put("/sets"));

        // THEN
        resultActions.andExpect(status().isNoContent());
        assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(aMongoSet, anotherMongoSet);
        assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(aMongoCard, anotherMongoCard, anExtraMongoCard);
    }

    @Test
    void should_return_all_sets_from_repository() throws Exception {
        // GIVEN
        mongoTemplate.insertAll(List.of(aMongoSet, anotherMongoSet).asJava());

        // WHEN
        ResultActions resultActions = mvc.perform(get("/sets"));

        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(aRestSet, anotherRestSet)), true));
    }
}
