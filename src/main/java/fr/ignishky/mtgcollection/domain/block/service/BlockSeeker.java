package fr.ignishky.mtgcollection.domain.block.service;

import fr.ignishky.mtgcollection.domain.block.BlockReference;
import fr.ignishky.mtgcollection.domain.block.BlockRepository;
import fr.ignishky.mtgcollection.domain.block.model.Block;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public class BlockSeeker implements BlockLoader {

    private final BlockReference blockReference;
    private final BlockRepository blockRepository;

    public BlockSeeker(BlockReference blockReference, BlockRepository blockRepository) {
        this.blockReference = blockReference;
        this.blockRepository = blockRepository;
    }

    @Override
    public void load() {
        List<Block> blocks = blockReference.loadAll();

        if (!blocks.isEmpty()) {
            blockRepository.save(blocks);
        }
    }
}
