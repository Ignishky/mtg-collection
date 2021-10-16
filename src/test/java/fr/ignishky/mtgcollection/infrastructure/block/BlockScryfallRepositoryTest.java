package fr.ignishky.mtgcollection.infrastructure.block;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall;
import fr.ignishky.mtgcollection.infrastructure.configuration.ScryfallProperties;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static fr.ignishky.mtgcollection.Blocks.*;
import static fr.ignishky.mtgcollection.ScryfallBlocks.aScryfallBlocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlockScryfallRepositoryTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ScryfallProperties scryfallProperties = new ScryfallProperties("http://scryfall.mtg-collection.test");

    private final BlockScryfallRepository blockScryfallRepository = new BlockScryfallRepository(restTemplate, scryfallProperties);

    @Test
    void should_return_empty_list_when_scryfall_response_is_missing() {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", BlockScryfall.class)).thenReturn(null);

        // WHEN
        List<Block> blocks = blockScryfallRepository.loadAll();

        // THEN
        assertThat(blocks).isEqualTo(List.empty());
    }

    @Test
    void should_return_empty_list_when_scryfall_data_are_missing() {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", BlockScryfall.class)).thenReturn(new BlockScryfall(null));

        // WHEN
        List<Block> blocks = blockScryfallRepository.loadAll();

        // THEN
        assertThat(blocks).isEqualTo(List.empty());
    }

    @Test
    void should_return_scryfall_sets_without_parent_nor_block_code() {
        // GIVEN
        when(restTemplate.getForObject("http://scryfall.mtg-collection.test/sets", BlockScryfall.class)).thenReturn(aScryfallBlocks());

        // WHEN
        List<Block> blocks = blockScryfallRepository.loadAll();

        // THEN
        assertThat(blocks).containsOnly(INNISTRAD_MIDNIGHT_HUNT, ADVENTURE_OF_THE_FORGOTTEN_REALMS);
    }
}
