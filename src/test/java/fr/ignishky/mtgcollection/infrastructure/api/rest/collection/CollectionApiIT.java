package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.fixtures.InstantFreezeExtension;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static fr.ignishky.mtgcollection.TestUtils.assertEvent;
import static fr.ignishky.mtgcollection.TestUtils.readJsonFile;
import static fr.ignishky.mtgcollection.fixtures.DomainFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument.toCardDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void clean() {
        mongoTemplate.remove(CardDocument.class).all();
        mongoTemplate.remove(EventDocument.class).all();
    }

    @Test
    void should_retrieve_all_card_from_collection() throws Exception {
        // GIVEN
        mongoTemplate.insertAll(List.of(toCardDocument(aOwnedCard), toCardDocument(anExtraOwnedCard), toCardDocument(anotherCard)).asJava());

        // WHEN
        var response = mvc.perform(get("/collection"));

        // THEN
        response.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readJsonFile("/collection/getCollectionResponse.json"), true)
        );
    }

    @Test
    void should_reject_adding_invalid_card_into_collection() throws Exception {
        // WHEN
        var response = mvc.perform(put("/collection/%s".formatted(aCard.id()))
                .contentType(APPLICATION_JSON)
                .content("{\"isFoiled\": false}"));

        // THEN
        response.andExpectAll(
                status().isNotFound()
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
    }

    @Test
    void should_add_existing_card_to_collection() throws Exception {
        // GIVEN
        mongoTemplate.save(toCardDocument(aCard));

        // WHEN
        var response = mvc.perform(put("/collection/%s".formatted(aCard.id()))
                .contentType(APPLICATION_JSON)
                .content("{\"isFoiled\": true}"));

        // THEN
        response.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readJsonFile("/collection/addCardToCollectionResponse.json"), true)
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toCardDocument(aOwnedCard));

        var eventDocuments = mongoTemplate.findAll(EventDocument.class);
        assertThat(eventDocuments).hasSize(1);
        assertEvent(eventDocuments.get(0), "Card", aCard.id().toString(), "CardOwned", "{\"isOwned\":true,\"isFoiled\":true}");
    }

}
