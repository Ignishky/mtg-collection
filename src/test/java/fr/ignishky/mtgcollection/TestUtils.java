package fr.ignishky.mtgcollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

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

}
