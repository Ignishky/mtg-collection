package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.common.InstantFreezeExtension;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static fr.ignishky.mtgcollection.TestUtils.assertEvent;
import static fr.ignishky.mtgcollection.TestUtils.readJsonFile;
import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument.toCardDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(InstantFreezeExtension.class)
class CollectionApiIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_reject_adding_missing_card_to_collection() throws Exception {

        // WHEN
        var resultActions = mvc.perform(put("/collection/%s".formatted(aCard.id()))
                .contentType(APPLICATION_JSON)
                .content("{\"isFoiled\": false}"));

        // THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void should_add_existing_card_to_collection() throws Exception {
        // GIVEN
        mongoTemplate.save(toCardDocument(aCard));

        // WHEN
        var resultActions = mvc.perform(put("/collection/%s".formatted(aCard.id()))
                .contentType(APPLICATION_JSON)
                .content("{\"isFoiled\": true}"));

        // THEN
        resultActions.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readJsonFile("/collection/collectionResponse.json"), true)
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toCardDocument(aOwnedCard));

        var eventDocuments = mongoTemplate.findAll(EventDocument.class);
        assertThat(eventDocuments).hasSize(1);
        assertEvent(eventDocuments.get(0), "Card", aCard.id().toString(), "CardOwned", "{\"isOwned\":true,\"isFoiled\":true}");
    }

}
