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

    public static ConfigLoader getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank()) return fromSystem;

        String fromEnv = System.getenv(key.toUpperCase().replace('.', '_'));
        if (fromEnv != null && !fromEnv.isBlank()) return fromEnv;
        return properties.getProperty(key);
    }

    public String getRequiredProperty(String key) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config key: " + key);
        }
        return value;
    }

    public long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) return defaultValue;
        return Long.parseLong(value.trim());
    }
}
