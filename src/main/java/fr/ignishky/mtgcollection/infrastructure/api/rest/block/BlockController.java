package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryBus;
import fr.ignishky.mtgcollection.infrastructure.api.rest.ApiResponseMapper;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.SetsResponse;
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
                .map(ApiResponseMapper::toBlockSummary);
        LOGGER.info("Respond to `GET /blocks` with {} blocks", blocks.size());
        return ok(new BlocksResponse(blocks));
    }

    @Override
    public ResponseEntity<SetsResponse> getSets(String blockCode) {
        LOGGER.info("Received call to `GET /blocks/{}`", blockCode);
        var getSetsResponse = queryBus.dispatch(new GetSetsQuery(new SetCode(blockCode)));
        var sets = getSetsResponse.sets()
                .map(ApiResponseMapper::toSetSummary);
        LOGGER.info("Respond to `GET /blocks/{}` with {} blocks", blockCode, sets.size());
        return ok(new SetsResponse(
                getSetsResponse.blockName().value(),
                getSetsResponse.nbCards(),
                getSetsResponse.nbOwned(),
                getSetsResponse.nbOwnedFoil(),
                sets)
        );
    }

}
