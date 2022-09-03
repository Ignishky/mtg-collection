package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static fr.ignishky.mtgcollection.TestUtils.readFile;
import static fr.ignishky.mtgcollection.fixtures.SetFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.block.BlockApi.BLOCK_ENDPOINT;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper.toSetDocument;
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
        mongoTemplate.dropCollection(SetDocument.class);
        mongoTemplate.insertAll(List.of(
                toSetDocument(KaldheimArtSeries),
                toSetDocument(KaldheimPromo),
                toSetDocument(KaldheimToken),
                toSetDocument(Kaldheim),
                toSetDocument(JudgeGiftCards2022),
                toSetDocument(StreetOfNewCapenna),
                toSetDocument(DuelDecks1)
        ).asJava());
    }

    @Test
    void should_return_all_blocks_from_repository() throws Exception {
        // WHEN
        ResultActions resultActions = mvc.perform(get(BLOCK_ENDPOINT));

        // THEN
        resultActions.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/block/allBlocksResponse.json"), true)
        );
    }

    @Test
    void should_return_all_sets_from_a_given_block() throws Exception {
        // WHEN
        ResultActions resultActions = mvc.perform(get(BLOCK_ENDPOINT + "/khm"));

        // THEN
        resultActions.andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(readFile("/block/khmSetsResponse.json"), true)
        );
    }

}
