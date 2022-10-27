package fr.ignishky.mtgcollection.domain.block.query;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.model.BlockCode;
import fr.ignishky.mtgcollection.domain.block.model.BlockIcon;
import fr.ignishky.mtgcollection.domain.block.model.BlockName;
import fr.ignishky.mtgcollection.domain.set.model.Set;
import fr.ignishky.mtgcollection.domain.set.model.SetCode;
import fr.ignishky.mtgcollection.domain.set.port.repository.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.domain.set.model.SetType.EXPANSION;
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
                .map(GetBlocksQueryHandler::toBlock);
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
                sets.map(Set::cardCount).sum(),
                sets.map(Set::cardOwnedCount).sum(),
                sets.map(Set::cardFoilOwnedCount).sum(),
                new BlockIcon(mainSet.icon().url()),
                null
        );
    }

}
