package com.innowise.gym.manager;

import java.io.InputStream;
import java.util.Properties;

public final class PropertyManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = PropertyManager.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("db.properties not found");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private PropertyManager() {
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
