package fr.ignishky.mtgcollection.domain.block;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.service.BlockLoader;
import fr.ignishky.mtgcollection.domain.block.service.BlockSeeker;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static fr.ignishky.mtgcollection.utils.DomainFixture.block;
import static org.mockito.Mockito.*;

class BlockSeekerTest {

    private final BlockReference blockReference = mock(BlockReference.class);
    private final BlockRepository blockRepository = mock(BlockRepository.class);

    private final BlockLoader blockLoader = new BlockSeeker(blockReference, blockRepository);

    @Test
    void should_not_save_empty_list_in_repository() {
        // GIVEN
        when(blockReference.loadAll()).thenReturn(List.of());

        // WHEN
        blockLoader.load();

        // THEN
        verifyNoInteractions(blockRepository);
    }

    @Test
    void should_get_all_blocks_and_save_them() {
        // GIVEN
        List<Block> blocks = List.of(
                block(1),
                block(2)
        );
        when(blockReference.loadAll()).thenReturn(blocks);

        // WHEN
        blockLoader.load();

        // THEN
        verify(blockRepository).save(blocks);
    }
}