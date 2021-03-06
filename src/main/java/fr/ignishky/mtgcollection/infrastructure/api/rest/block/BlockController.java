package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse.BlockSummary;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse.SetSummary;
import fr.ignishky.mtgcollection.query.block.GetBlocksQuery;
import fr.ignishky.mtgcollection.query.set.GetSetsQuery;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

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
        var blocks = queryBus.dispatch(new GetBlocksQuery())
                .map(BlockSummary::toBlockSummary);
        LOGGER.info("Respond to `GET /blocks` with {} blocks", blocks.size());
        return ok(new BlocksResponse(blocks));
    }

    @Override
    public ResponseEntity<SetsResponse> getSets(String blockCode) {
        LOGGER.info("Received call to `GET /blocks/{}`", blockCode);
        var blocks = queryBus.dispatch(new GetSetsQuery(new SetCode(blockCode)))
                .map(SetSummary::toSetSummary);
        LOGGER.info("Respond to `GET /blocks/{}` with {} blocks", blockCode, blocks.size());
        return ok(new SetsResponse(blocks));
    }

}
