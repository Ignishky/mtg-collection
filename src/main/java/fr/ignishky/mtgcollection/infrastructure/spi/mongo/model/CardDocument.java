package fr.ignishky.mtgcollection.infrastructure.spi.mongo.model;

import fr.ignishky.mtgcollection.domain.card.model.OwnState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document("cards")
public record CardDocument(
        @Id
        UUID id,
        String setCode,
        String name,
        String image,
        List<String> finishes,
        PriceRecord prices,
        OwnState ownState,
        LocalDate lastUpdate
) {

}
