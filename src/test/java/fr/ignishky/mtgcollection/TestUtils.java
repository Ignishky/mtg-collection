package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.set.SetId;
import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static java.lang.String.join;
import static java.nio.file.Files.readAllLines;
import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static String readFile(String fileName) {
        try {
            return join("", readAllLines(Path.of(TestUtils.class.getResource(fileName).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void assertEvent(EventDocument event, SetId aggregateId, String name, String payload) {
        assertThat(event.aggregateName()).isEqualTo("Set");
        assertThat(event.aggregateId()).isEqualTo(aggregateId.toString());
        assertThat(event.name()).isEqualTo(name);
        assertThat(event.payload()).isEqualTo(payload);
    }

    public static void assertEvent(EventDocument event, CardId aggregateId, String name, String payload) {
        assertThat(event.aggregateName()).isEqualTo("Card");
        assertThat(event.aggregateId()).isEqualTo(aggregateId.toString());
        assertThat(event.name()).isEqualTo(name);
        assertThat(event.payload()).isEqualTo(payload);
    }

}
