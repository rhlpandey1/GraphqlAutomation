package org.graphql.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class QueryLoader {

    private QueryLoader() {
        // Utility class: do not create an object.
    }

    public static String load(String fileName) {

        String resourcePath = "graphql/" + fileName;

        try (InputStream inputStream =
                     QueryLoader.class.getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "GraphQL query file was not found: " + resourcePath
                );
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to read GraphQL query file: " + resourcePath,
                    exception
            );
        }
    }
}