package fr.ignishky.mtgcollection.infrastructure.block;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.BlockRepository;
import fr.ignishky.mtgcollection.infrastructure.block.model.BlockMongo;
import io.vavr.collection.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import static fr.ignishky.mtgcollection.domain.block.model.Block.toBlock;
import static java.util.stream.Collectors.toList;

@Repository
public interface BlockMongoRepository extends BlockRepository, MongoRepository<BlockMongo, String> {

    @Override
    default void save(List<Block> blocks){
        saveAll(blocks.map(block -> new BlockMongo(block.id().uuid().toString(), block.code(), block.name())));
    }

    @Override
    default List<Block> getAll(){
        return List.ofAll(findAll().stream()
                .map(blockMongo -> toBlock(blockMongo.id(), blockMongo.code(), blockMongo.name()))
                .collect(toList()));
    }
}
