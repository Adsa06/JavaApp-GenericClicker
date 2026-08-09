package io.github.adsa06.presentation.ui.theme;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.googlecode.lanterna.graphics.Theme;

import io.github.adsa06.data.repository.SettingsRepository;
import io.github.adsa06.domain.service.JsonService;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;

public class ThemeManagerTest {

    private ThemeManager themeManager;

    @BeforeEach
    void setUp() {
        JsonService jsonService = mock(JsonService.class);

        themeManager = new ThemeManager(jsonService);
    }

    @Test
    void shouldReturnMinimalistTheme() {
        Theme theme = themeManager.getTheme(ThemeType.MINIMALIST);

        assertNotNull(theme);
    }

    @Test
    void shouldReturnSameThemeInstanceForSameType() {
        Theme first = themeManager.getTheme(ThemeType.MINIMALIST);
        Theme second = themeManager.getTheme(ThemeType.MINIMALIST);

        assertSame(first, second);
    }
}
