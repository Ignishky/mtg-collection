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
import static fr.ignishky.mtgcollection.Blocks.*;
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
        mongoTemplate.save(aMongoBlock(INNISTRAD_MIDNIGHT_HUNT));
        mongoTemplate.save(aMongoBlock(ADVENTURE_OF_THE_FORGOTTEN_REALMS));

        // WHEN
        AggregatedHttpResponse response = client.get("/blocks").aggregate().join();

        // THEN
        assertThat(response.status()).isEqualTo(OK);
        assertThat(response.contentType()).isEqualTo(JSON_UTF_8);
        assertThat(response.contentUtf8()).isEqualTo("[" +
                "{\"id\":{\"uuid\":\"44b8eb8f-fa23-401a-98b5-1fbb9871128e\"},\"code\":\"mid\",\"name\":\"Innistrad: Midnight hunt\"}," +
                "{\"id\":{\"uuid\":\"e1ef87ba-ba92-4573-817f-543b996d2851\"},\"code\":\"afr\",\"name\":\"Adventures in the Forgotten Realms\"}" +
                "]");
    }
}