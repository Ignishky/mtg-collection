package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.event.CardRetired;
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
import static fr.ignishky.mtgcollection.TestUtils.readFile;
import static fr.ignishky.mtgcollection.fixtures.CardFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.CollectionApi.COLLECTION_PATH;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toCardDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mongoTemplate.insertAll(List.of(toCardDocument(anOwnedCard), toCardDocument(anExtraOwnedCard), toCardDocument(anotherCard)).asJava());

        // WHEN
        var response = mvc.perform(get(COLLECTION_PATH));

        // THEN
        response.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/collection/getCollectionResponse.json"), true)
        );
    }

    @Test
    void should_reject_adding_unknown_card_into_collection() throws Exception {
        // WHEN
        var response = mvc.perform(put("%s/%s".formatted(COLLECTION_PATH, aCard.id()))
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
    void should_add_card_to_collection() throws Exception {
        // GIVEN
        mongoTemplate.save(toCardDocument(aCard));

        // WHEN
        var response = mvc.perform(put("%s/%s".formatted(COLLECTION_PATH, aCard.id()))
                .contentType(APPLICATION_JSON)
                .content("{\"isFoiled\": true}"));

        // THEN
        response.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/collection/addCardToCollectionResponse.json"), true)
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toCardDocument(anOwnedCard));

        var eventDocuments = mongoTemplate.findAll(EventDocument.class);
        assertThat(eventDocuments).hasSize(1);
        assertEvent(eventDocuments.get(0), aCard.id(), CardOwned.class, "{\"isOwned\":true,\"isFoiled\":true}");
    }

    @Test
    void should_reject_deleting_unknown_card_from_collection() throws Exception {
        // WHEN
        var response = mvc.perform(delete("%s/%s".formatted(COLLECTION_PATH, aCard.id())));

        // THEN
        response.andExpectAll(
                status().isNotFound()
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
        assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
    }

    @Test
    void should_delete_card_from_collection() throws Exception {
        // GIVEN
        mongoTemplate.save(toCardDocument(anOwnedCard));

        // WHEN
        var response = mvc.perform(delete("%s/%s".formatted(COLLECTION_PATH, aCard.id())));

        // THEN
        response.andExpectAll(
                status().isOk()
        );
        assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toCardDocument(aCard));

        var eventDocuments = mongoTemplate.findAll(EventDocument.class);
        assertThat(eventDocuments).hasSize(1);
        assertEvent(eventDocuments.get(0), aCard.id(), CardRetired.class, "{}");
    }

}
