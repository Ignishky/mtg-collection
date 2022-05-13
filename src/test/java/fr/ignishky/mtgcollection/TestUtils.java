package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.infrastructure.spi.mongo.model.EventDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static String readJsonFile(String fileName) {
        InputStream resource = TestUtils.class.getResourceAsStream(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Unable to find file : " + fileName);
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {

            return reader.lines().collect(joining(""));

        } catch (IOException e) {
            throw new IllegalArgumentException("IOException thrown while reading test file: ", e);
        }
    }

    public static void assertEvent(EventDocument event, String aggregateName, String aggregateId, String name, String payload) {
        assertThat(event.aggregateName()).isEqualTo(aggregateName);
        assertThat(event.aggregateId()).isEqualTo(aggregateId);
        assertThat(event.name()).isEqualTo(name);
        assertThat(event.payload()).isEqualTo(payload);
    }

}
