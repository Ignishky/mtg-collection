package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.domain.block.query.GetBlocksQuery;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.domain.block.query.GetBlockQuery;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlockResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.mapper.BlockMapper.toBlockResponse;
import static fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.mapper.BlockMapper.toBlocksResponse;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class BlockController implements BlockApi {

    private static final Logger LOGGER = getLogger(BlockController.class);

    private final QueryBus queryBus;

    public BlockController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @Override
    public ResponseEntity<BlocksResponse> getAll() {
        LOGGER.info("Received call to `GET /blocks`");
        var blocks = queryBus.dispatch(new GetBlocksQuery());
        LOGGER.info("Respond to `GET /blocks` with {} blocks", blocks.size());
        return ok(toBlocksResponse(blocks));
    }

    @Override
    public ResponseEntity<BlockResponse> getBlock(String blockCode) {
        LOGGER.info("Received call to `GET /blocks/{}`", blockCode);
        var block = queryBus.dispatch(new GetBlockQuery(new SetCode(blockCode)));
        LOGGER.info("Respond to `GET /blocks/{}` with {} blocks", blockCode, block.sets().size());
        return ok(toBlockResponse(block));
    }

}
