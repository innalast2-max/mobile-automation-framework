package com.mobileframework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private static final ConfigLoader INSTANCE = new ConfigLoader();
    private final Properties properties = new Properties();

    private ConfigLoader() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static ConfigLoader getInstance() { return INSTANCE; }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
