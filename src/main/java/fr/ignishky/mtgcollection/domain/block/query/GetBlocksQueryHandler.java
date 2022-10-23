package fr.ignishky.mtgcollection.domain.block.query;

import fr.ignishky.mtgcollection.domain.block.Block;
import fr.ignishky.mtgcollection.domain.block.BlockCode;
import fr.ignishky.mtgcollection.domain.block.BlockIcon;
import fr.ignishky.mtgcollection.domain.block.BlockName;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.domain.set.SetType.EXPANSION;
import static java.util.Comparator.comparing;

@Component
public class GetBlocksQueryHandler implements QueryHandler<GetBlocksQuery, List<Block>> {

    private final SetRepository setRepository;
    private final MongoTemplate mongoTemplate;

    public GetBlocksQueryHandler(SetRepository setRepository, MongoTemplate mongoTemplate) {
        this.setRepository = setRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Block> handle(GetBlocksQuery query) {
        return setRepository.getAll()
                .filter(set -> set.setType() == EXPANSION)
                .sorted(comparing(Set::releasedDate).reversed())
                .map(block -> retrieveSetsList(block.code()))
                .map(sets -> toBlock(sets));
    }

    private List<Set> retrieveSetsList(SetCode setCode) {
        return List.ofAll(mongoTemplate.findAll(SetDocument.class))
                .map(MongoDocumentMapper::toSet)
                .filter(set -> set.code().equals(setCode) || set.parentSetCode().contains(setCode))
                .sorted(Set::compareTo);
    }

    private static Block toBlock(List<Set> sets) {
        Set mainSet = sets.head();
        return new Block(
                new BlockCode(mainSet.code().value()),
                new BlockName(mainSet.name().value()),
                sets.map(Set::cardCount).sum().intValue(),
                sets.map(Set::cardOwnedCount).sum().intValue(),
                sets.map(Set::cardFoilOwnedCount).sum().intValue(),
                new BlockIcon(mainSet.icon().url())
        );
    }

}
