package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfall;
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

import static fr.ignishky.mtgcollection.common.SpiFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    }

    @Test
    void should_load_sets_from_scryfall_and_save_them_into_mongo() throws Exception {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", SetScryfall.class)).thenReturn(aScryfallSets);

        // WHEN
        ResultActions resultActions = mvc.perform(put("/sets"));

        // THEN
        resultActions.andExpect(status().isNoContent());
        assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(aMongoSet, anotherMongoSet);
    }
}