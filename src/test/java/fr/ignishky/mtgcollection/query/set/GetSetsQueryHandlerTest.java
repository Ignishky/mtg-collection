package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.SetDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import static fr.ignishky.mtgcollection.common.DomainFixtures.*;
import static fr.ignishky.mtgcollection.common.SpiFixtures.aMongoSet;
import static fr.ignishky.mtgcollection.common.SpiFixtures.anotherMongoSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSetsQueryHandlerTest {

    private final MongoTemplate mongoTemplate = mock(MongoTemplate.class);

    private final GetSetsQueryHandler handler = new GetSetsQueryHandler(mongoTemplate);

    @Test
    void should_get_card_set_from_repository() {
        when(mongoTemplate.findAll(SetDocument.class)).thenReturn(List.of(aMongoSet, anotherMongoSet).asJava());

        List<Set> sets = handler.handle(new GetSetsQuery());

        assertThat(sets).containsOnly(aSet, anotherSet);
    }

}