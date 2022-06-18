package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.framework.cqrs.query.QueryHandler;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class GetSetsQueryHandler implements QueryHandler<GetSetsQuery, List<Set>> {

    private final MongoTemplate mongoTemplate;

    public GetSetsQueryHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Set> handle(GetSetsQuery query) {
        return List.ofAll(mongoTemplate.findAll(SetDocument.class))
                .map(SetDocument::toSet)
                .sorted(Comparator.comparing(Set::releasedDate).reversed());
    }

}
