package fr.ignishky.mtgcollection.domain.block;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import io.vavr.collection.List;

public interface BlockRepository {

    void save(List<Block> blocks);

    List<Block> getAll();

}
