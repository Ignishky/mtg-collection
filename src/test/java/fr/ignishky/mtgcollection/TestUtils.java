package fr.ignishky.mtgcollection;

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
            throw new RuntimeException(e);
        }
    }

    public static void assertEvent(EventDocument event, String aggregateName, String aggregateId, String name, String payload) {
        assertThat(event.aggregateName()).isEqualTo(aggregateName);
        assertThat(event.aggregateId()).isEqualTo(aggregateId);
        assertThat(event.name()).isEqualTo(name);
        assertThat(event.payload()).isEqualTo(payload);
    }

}
