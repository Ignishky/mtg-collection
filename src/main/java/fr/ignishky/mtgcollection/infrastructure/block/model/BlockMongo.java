package fr.ignishky.mtgcollection.infrastructure.block.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("blocks")
public record BlockMongo(
        @Id
        String id,
        String code,
        String name
) {
}
