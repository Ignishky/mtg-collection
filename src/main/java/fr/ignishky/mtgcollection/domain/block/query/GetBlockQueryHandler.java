package fr.ignishky.mtgcollection.domain.block.query;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.model.BlockName;
import fr.ignishky.mtgcollection.domain.block.exception.BlockNotFoundException;
import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GetBlockQueryHandler implements QueryHandler<GetBlockQuery, Block> {

    private final MongoTemplate mongoTemplate;

    public GetBlockQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Block handle(GetBlockQuery query) {
        var sets = retrieveSetsList(query);

        if (sets.isEmpty()) {
            throw new BlockNotFoundException();
        }

        var blockName = sets.find(set -> set.code().equals(query.setCode())).map(Set::name).get();
        return new Block(
                null,
                new BlockName(blockName.value()),
                sets.map(Set::cardCount).sum(),
                sets.map(Set::cardOwnedCount).sum(),
                sets.map(Set::cardFoilOwnedCount).sum(),
                null,
                sets
        );
    }

    private List<Set> retrieveSetsList(GetBlockQuery query) {
        return List.ofAll(mongoTemplate.findAll(SetDocument.class))
                .map(MongoDocumentMapper::toSet)
                .filter(set -> set.code().equals(query.setCode()) || set.parentSetCode().contains(query.setCode()))
                .sorted(Set::compareTo);
    }

}
