package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.MongoDocumentMapper;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GetSetsQueryHandler implements QueryHandler<GetSetsQuery, GetSetsResponse> {

    private final MongoTemplate mongoTemplate;

    public GetSetsQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public GetSetsResponse handle(GetSetsQuery query) {
        var sets = retrieveSetsList(query);
        var blockName = sets.find(set -> set.code().equals(query.setCode())).map(Set::name).get();
        return new GetSetsResponse(
                blockName,
                sets
        );
    }

    private List<Set> retrieveSetsList(GetSetsQuery query) {
        return List.ofAll(mongoTemplate.findAll(SetDocument.class))
                .map(MongoDocumentMapper::toSet)
                .filter(set -> set.code().equals(query.setCode()) || set.parentSetCode().contains(query.setCode()))
                .sorted(Set::compareTo);
    }

}
