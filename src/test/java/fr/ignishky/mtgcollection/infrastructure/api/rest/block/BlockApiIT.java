package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static fr.ignishky.mtgcollection.TestUtils.readFile;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.block.BlockApi.BLOCK_PATH;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toDocument;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlockApiIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(EventDocument.class);
        mongoTemplate.dropCollection(SetDocument.class);
        mongoTemplate.dropCollection(CardDocument.class);

        mongoTemplate.insertAll(List.of(
                toDocument(AKHM),
                toDocument(PKHM),
                toDocument(TKHM),
                toDocument(KHM.withCardOwnedCount(1)),
                toDocument(P22),
                toDocument(SNC),
                toDocument(DDU)
        ).asJava());
    }

    @Nested
    class getAll {

        @Test
        void should_return_all_blocks_from_repository() throws Exception {
            // WHEN
            ResultActions resultActions = mvc.perform(get(BLOCK_PATH));

            // THEN
            resultActions.andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON),
                    content().json(readFile("/block/allBlocksResponse.json"), true)
            );
        }

    }

    @Nested
    class GetBlock {

        @Test
        void should_return_404_when_block_code_is_unknown() throws Exception {
            // WHEN
            ResultActions resultActions = mvc.perform(get("%s/%s".formatted(BLOCK_PATH, "FAKE")));

            // THEN
            resultActions.andExpectAll(
                    status().isNotFound()
            );
        }

        @Test
        void should_return_all_sets_from_a_given_block() throws Exception {
            // WHEN
            ResultActions resultActions = mvc.perform(get("%s/%s".formatted(BLOCK_PATH, KHM.code())));

            // THEN
            resultActions.andExpectAll(
                    status().isOk(),
                    content().contentType(APPLICATION_JSON),
                    content().json(readFile("/block/khmSetsResponse.json"), true)
            );
        }

    }

}
