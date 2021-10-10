package fr.ignishky.mtgcollection.application.query;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;
import fr.ignishky.mtgcollection.application.AnnotatedService;
import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.BlockRepository;
import io.vavr.collection.List;
import org.springframework.stereotype.Controller;

@Controller
public class BlockQuery implements AnnotatedService {

    private final BlockRepository blockRepository;

    public BlockQuery(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Get("/blocks")
    @ProducesJson
    public List<Block> getAllBlocks() {
        return blockRepository.getAll();
    }
}
