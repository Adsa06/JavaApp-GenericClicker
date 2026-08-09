package io.github.adsa06.presentation.ui.theme;

import java.util.EnumMap;
import java.util.Map;

import com.googlecode.lanterna.graphics.Theme;

import io.github.adsa06.domain.service.JsonService;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class ThemeManager {

    public enum ThemeType {
        MINIMALIST
    }

    private Map<ThemeType, Theme> themes = new EnumMap<>(ThemeType.class);

    public ThemeManager(JsonService jsonService) {
        themes.putAll(jsonService.readThemes());
    }

    public Theme getTheme(ThemeType theme) {
        return themes.get(theme);
    }
}
