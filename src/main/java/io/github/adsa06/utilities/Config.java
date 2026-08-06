package io.github.adsa06.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class Config {
    private final Properties PROPERTIES = new Properties();

    public Config() {
        try (InputStream input = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró config.properties");
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar config.properties", e);
        }
    }

    /**
     * Obtiene cualquier propiedad.
     */
    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Obtiene una propiedad con valor por defecto.
     */
    public String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
