package io.github.adsa06.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    void shouldLoadPropertyFromTestResources() {
        Config config = new Config();

        assertEquals("db local name", config.get("db.local.name"));
    }

    @Test
    void shouldReturnDefaultValueWhenPropertyMissing() {
        Config config = new Config();

        assertEquals("default", config.get("missing.property", "default"));
    }
}
