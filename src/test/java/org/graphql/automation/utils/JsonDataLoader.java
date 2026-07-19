package org.graphql.automation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class JsonDataLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonDataLoader() {
        // Utility class: do not create an object.
    }

    public static Map<String, Object> loadAsMap(String fileName) {

        String resourcePath = "testdata/" + fileName;

        try (InputStream inputStream =
                     JsonDataLoader.class.getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "JSON test-data file was not found: " + resourcePath
                );
            }

            return OBJECT_MAPPER.readValue(
                    inputStream,
                    new TypeReference<Map<String, Object>>() {
                    }
            );

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to read JSON test-data file: " + resourcePath,
                    exception
            );
        }
    }
}