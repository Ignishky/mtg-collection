package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.event.CardOwned;
import fr.ignishky.mtgcollection.domain.card.event.CardRetired;
import fr.ignishky.mtgcollection.domain.set.event.SetUpdated;
import fr.ignishky.mtgcollection.fixtures.InstantFreezeExtension;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.SNC;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.CollectionApi.COLLECTION_PATH;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toDocument;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(InstantFreezeExtension.class)
class CollectionApiIT {

    private static final Card ledgerShredderOwnedFoil = ledgerShredder.withOwned(true).withOwnedFoil(true);

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clean() {
        mongoTemplate.dropCollection(EventDocument.class);
        mongoTemplate.dropCollection(SetDocument.class);
        mongoTemplate.dropCollection(CardDocument.class);
    }

    @Test
    void should_retrieve_all_card_from_collection() throws Exception {
        // GIVEN
        mongoTemplate.insertAll(List.of(
                toDocument(ledgerShredder.withOwned(true).withOwnedFoil(true)),
                toDocument(depopulate.withOwned(true)),
                toDocument(vorinclex)
        ).asJava());

        // WHEN
        var response = mvc.perform(get(COLLECTION_PATH));

        // THEN
        response.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/collection/getCollectionResponse.json"), true)
        );
    }

    @Nested
    class AddCardToCollection {

        @Test
        void should_reject_adding_unknown_card() throws Exception {
            // WHEN
            var response = mvc.perform(put("%s/%s".formatted(COLLECTION_PATH, randomUUID()))
                    .contentType(APPLICATION_JSON)
                    .content("{\"isOwnedFoil\": false}"));

            // THEN
            response.andExpect(status().isNotFound());

            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
        }

        @Test
        void should_accept_to_add_owned_card() throws Exception {
            // GIVEN
            mongoTemplate.insertAll(List.of(
                    toDocument(SNC.withCardOwnedCount(1).withCardFoilOwnedCount(1)),
                    toDocument(ledgerShredderOwnedFoil)
            ).asJava());

            // WHEN
            var response = mvc.perform(put("%s/%s".formatted(COLLECTION_PATH, ledgerShredder.id()))
                    .contentType(APPLICATION_JSON)
                    .content("{\"isOwnedFoil\": true}"));

            // THEN
            response.andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON),
                    content().json(readFile("/collection/addCardToCollectionResponse.json"), true)
            );
            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredderOwnedFoil));

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(1);
            assertEvent(eventDocuments.get(0), ledgerShredder.id(), CardOwned.class, "{\"isOwned\":true,\"isOwnedFoil\":true}");

            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredderOwnedFoil));
            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC.withCardOwnedCount(1).withCardFoilOwnedCount(1)));
        }

        @Test
        void should_add_not_owned_card() throws Exception {
            // GIVEN
            mongoTemplate.insertAll(List.of(
                    toDocument(SNC),
                    toDocument(ledgerShredder)
            ).asJava());

            // WHEN
            var response = mvc.perform(put("%s/%s".formatted(COLLECTION_PATH, ledgerShredder.id()))
                    .contentType(APPLICATION_JSON)
                    .content("{\"isOwnedFoil\": true}"));

            // THEN
            response.andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON),
                    content().json(readFile("/collection/addCardToCollectionResponse.json"), true)
            );

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(2);
            assertEvent(eventDocuments.get(0), ledgerShredder.id(), CardOwned.class, "{\"isOwned\":true,\"isOwnedFoil\":true}");
            assertEvent(eventDocuments.get(1), SNC.id(), SetUpdated.class, "{\"cardOwnedCount\":1,\"cardFoilOwnedCount\":1}");

            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredderOwnedFoil));
            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC.withCardOwnedCount(1).withCardFoilOwnedCount(1)));
        }

    }

    @Nested
    class DeleteCardFromCollection {

        @Test
        void should_reject_deleting_unknown_card() throws Exception {
            // WHEN
            var response = mvc.perform(delete("%s/%s".formatted(COLLECTION_PATH, randomUUID())));

            // THEN
            response.andExpect(status().isNotFound());

            assertThat(mongoTemplate.findAll(CardDocument.class)).isEmpty();
            assertThat(mongoTemplate.findAll(EventDocument.class)).isEmpty();
        }

        @Test
        void should_accept_deleting_not_owned_card() throws Exception {
            // GIVEN
            mongoTemplate.insertAll(List.of(
                    toDocument(SNC),
                    toDocument(ledgerShredder)
            ).asJava());

            // WHEN
            var response = mvc.perform(delete("%s/%s".formatted(COLLECTION_PATH, ledgerShredder.id())));

            // THEN
            response.andExpect(status().isOk());

            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredder));

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(1);
            assertEvent(eventDocuments.get(0), ledgerShredder.id(), CardRetired.class, "{}");

            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredder));
            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC));
        }

        @Test
        void should_delete_owned_card() throws Exception {
            // GIVEN
            mongoTemplate.insertAll(List.of(
                    toDocument(SNC.withCardOwnedCount(1).withCardFoilOwnedCount(1)),
                    toDocument(ledgerShredderOwnedFoil)
            ).asJava());

            // WHEN
            var response = mvc.perform(delete("%s/%s".formatted(COLLECTION_PATH, ledgerShredder.id())));

            // THEN
            response.andExpect(status().isOk());

            var eventDocuments = mongoTemplate.findAll(EventDocument.class);
            assertThat(eventDocuments).hasSize(2);
            assertEvent(eventDocuments.get(0), ledgerShredder.id(), CardRetired.class, "{}");
            assertEvent(eventDocuments.get(1), SNC.id(), SetUpdated.class, "{\"cardOwnedCount\":0,\"cardFoilOwnedCount\":0}");

            assertThat(mongoTemplate.findAll(CardDocument.class)).containsOnly(toDocument(ledgerShredder));
            assertThat(mongoTemplate.findAll(SetDocument.class)).containsOnly(toDocument(SNC));
        }

    }

}
