package fr.ignishky.mtgcollection.infrastructure.block;

import fr.ignishky.mtgcollection.domain.block.BlockReference;
import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockScryfall;
import fr.ignishky.mtgcollection.infrastructure.configuration.ScryfallProperties;
import io.vavr.collection.List;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static fr.ignishky.mtgcollection.domain.block.model.Block.toBlock;

@Repository
public class BlockScryfallRepository implements BlockReference {

    private static final List<String> WANTED_SET_TYPE = List.of("expansion");

    private final RestTemplate restTemplate;
    private final ScryfallProperties scryfallProperties;

    public BlockScryfallRepository(RestTemplate restTemplate, ScryfallProperties scryfallProperties) {
        this.restTemplate = restTemplate;
        this.scryfallProperties = scryfallProperties;
    }

    @Override
    public List<Block> loadAll() {
        BlockScryfall blockScryfall = restTemplate.getForObject(scryfallProperties.getBaseUrl() + "/sets", BlockScryfall.class);
        if (blockScryfall == null || blockScryfall.data() == null) {
            return List.empty();
        }

        return blockScryfall.data()
                .filter(scryfallData -> LocalDate.now().isAfter(LocalDate.parse(scryfallData.released_at())))
                .filter(scryfallData -> WANTED_SET_TYPE.contains(scryfallData.set_type()))
                .map(scryfallData -> toBlock(scryfallData.id(), scryfallData.code(), scryfallData.name()));
    }
}
