package org.graphql.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream =
                     ConfigManager.class.getClassLoader()
                             .getResourceAsStream("config/config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "config.properties file was not found in src/test/resources/config"
                );
            }

            PROPERTIES.load(inputStream);

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to load config.properties",
                    exception
            );
        }
    }

    private ConfigManager() {
        // Prevent object creation; this class is used statically.
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Missing or empty configuration value for key: " + key
            );
        }

        return value.trim();
    }

    public static String getGraphqlUrl() {
        return get("base.url") + get("graphql.endpoint");
    }
}