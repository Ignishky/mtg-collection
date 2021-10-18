package fr.ignishky.mtgcollection.application.query;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;
import fr.ignishky.mtgcollection.application.AnnotatedService;
import fr.ignishky.mtgcollection.application.model.BlockResponse;
import fr.ignishky.mtgcollection.application.model.mapper.BlockMapper;
import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.BlockRepository;
import io.vavr.collection.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class BlockQuery implements AnnotatedService {

    private final BlockRepository blockRepository;

    public BlockQuery(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Get("/blocks")
    @ProducesJson
    public List<BlockResponse> getAllBlocks() {
        return blockRepository.getAll().map(BlockMapper::toBlockResponse);
    }
}
