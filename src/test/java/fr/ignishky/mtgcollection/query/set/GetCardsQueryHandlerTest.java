package fr.ignishky.mtgcollection.query.set;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static fr.ignishky.mtgcollection.fixtures.DomainFixtures.*;
import static fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.CardDocument.toCardDocument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

class GetCardsQueryHandlerTest {

    private final MongoTemplate mongoTemplate = mock(MongoTemplate.class);

    private final GetCardsQueryHandler queryHandler = new GetCardsQueryHandler(mongoTemplate);

    @Test
    void should_get_cards_of_a_given_set_from_repository() {
        // GIVEN
        Query mongoQuery = new Query().addCriteria(where("setCode").is(aSet.code().value()));
        when(mongoTemplate.find(mongoQuery, CardDocument.class))
                .thenReturn(List.of(toCardDocument(aCard), toCardDocument(anExtraCard)).asJava());
        GetCardsQuery query = new GetCardsQuery(aSet.code());

        // WHEN
        List<Card> cards = queryHandler.handle(query);

        // THEN
        assertThat(cards).containsOnly(aCard, anExtraCard);
    }
}
