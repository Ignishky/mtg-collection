package fr.ignishky.mtgcollection.application.command;

import fr.ignishky.mtgcollection.domain.block.service.BlockLoader;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BlockCommandTest {

    private final BlockLoader blockLoader = mock(BlockLoader.class);

    private final BlockCommand blockCommand = new BlockCommand(blockLoader);

    @Test
    void should_trigger_block_loader() {
        // GIVEN
        // WHEN
        blockCommand.loadAll();

        // THEN
        verify(blockLoader).load();
    }
}
