package com.pos.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Lay thong tin cau hinh tu file config.properties
public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Cannot find config.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    // Lay gia tri cua api.url.dev trong file config.properties
    public static String getApiUrl() {
        return properties.getProperty("api.url.dev", "");
    }
}
