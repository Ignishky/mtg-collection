package fr.ignishky.mtgcollection.integration_tests;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.linecorp.armeria.common.HttpStatus.OK;
import static com.linecorp.armeria.common.MediaType.JSON_UTF_8;
import static fr.ignishky.mtgcollection.Blocks.CORE_SET_2020;
import static fr.ignishky.mtgcollection.Blocks.SCOURGE;
import static fr.ignishky.mtgcollection.MongoBlocks.aMongoBlock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class BlockQueryIT {

    @Autowired
    private Server server;
    @Autowired
    private MongoTemplate mongoTemplate;

    private WebClient client;

    @BeforeEach
    void setUp() {
        client = WebClient.of("http://localhost:" + server.activeLocalPort());
    }

    @Test
    void should_return_all_blocks() {
        // GIVEN
        mongoTemplate.save(aMongoBlock(SCOURGE));
        mongoTemplate.save(aMongoBlock(CORE_SET_2020));

        // WHEN
        AggregatedHttpResponse response = client.get("/blocks").aggregate().join();

        // THEN
        assertThat(response.status()).isEqualTo(OK);
        assertThat(response.contentType()).isEqualTo(JSON_UTF_8);
        assertThat(response.contentUtf8()).isEqualTo("[" +
                "{\"id\":{\"uuid\":\"5133c3a1-1412-4ce6-a1f0-73b695966ded\"},\"code\":\"scg\",\"name\":\"Scourge\"}," +
                "{\"id\":{\"uuid\":\"4a787360-9767-4f44-80b1-2405dc5e39c7\"},\"code\":\"m20\",\"name\":\"Core Set 2020\"}" +
                "]");
    }
}