package fr.ignishky.mtgcollection.integration_tests;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.server.Server;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockMongo;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import static com.linecorp.armeria.common.HttpStatus.NO_CONTENT;
import static fr.ignishky.mtgcollection.Blocks.CORE_SET_2020;
import static fr.ignishky.mtgcollection.Blocks.SCOURGE;
import static fr.ignishky.mtgcollection.MongoBlocks.aMongoBlock;
import static fr.ignishky.mtgcollection.ScryfallBlocks.aScryfallBlocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class BlockCommandIT {

    @Autowired
    private Server server;
    @Autowired
    private MongoTemplate mongoTemplate;
    @MockBean
    private RestTemplate restTemplate;

    private WebClient client;

    @BeforeEach
    void setUp() {
        client = WebClient.of("http://localhost:" + server.activeLocalPort());
    }

    @Test
    void should_load_all_blocks() {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", BlockScryfall.class)).thenReturn(aScryfallBlocks());

        // WHEN
        AggregatedHttpResponse response = client.put("/blocks", "").aggregate().join();

        // THEN
        assertThat(response.status()).isEqualTo(NO_CONTENT);
        assertThat(mongoTemplate.findAll(BlockMongo.class)).containsOnly(
                aMongoBlock(SCOURGE),
                aMongoBlock(CORE_SET_2020)
        );
    }
}
