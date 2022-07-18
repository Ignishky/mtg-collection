package fr.ignishky.mtgcollection.query.block;

import fr.ignishky.mtgcollection.domain.block.Block;
import fr.ignishky.mtgcollection.domain.block.BlockCode;
import fr.ignishky.mtgcollection.domain.block.BlockIcon;
import fr.ignishky.mtgcollection.domain.block.BlockName;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.domain.set.SetType.expansion;
import static java.util.Comparator.comparing;

@Component
public class GetBlocksQueryHandler implements QueryHandler<GetBlocksQuery, List<Block>> {

    private final SetRepository setRepository;

    public GetBlocksQueryHandler(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public List<Block> handle(GetBlocksQuery query) {
        return setRepository.getAll()
                .filter(set -> set.setType() == expansion)
                .sorted(comparing(Set::releasedDate).reversed())
                .map(GetBlocksQueryHandler::toBlock);
    }

    private static Block toBlock(Set set) {
        return new Block(new BlockCode(set.code().value()), new BlockName(set.name().value()), new BlockIcon(set.icon().url()));
    }

}
