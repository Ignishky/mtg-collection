package fr.ignishky.mtgcollection.application.query;

import fr.ignishky.mtgcollection.domain.block.BlockRepository;
import fr.ignishky.mtgcollection.domain.block.model.Block;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static fr.ignishky.mtgcollection.utils.DomainFixture.block;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockQueryTest {

    private final BlockRepository blockRepository = mock(BlockRepository.class);

    private final BlockQuery blockQuery = new BlockQuery(blockRepository);

    @Test
    public void should_return_repository_blocks() {
        // GIVEN
        List<Block> blocks = List.of(
                block(1),
                block(2),
                block(3)
        );
        when(blockRepository.getAll()).thenReturn(blocks);

        // WHEN
        List<Block> allBlocks = blockQuery.getAllBlocks();

        // THEN
        assertThat(allBlocks).isEqualTo(blocks);
    }
}
