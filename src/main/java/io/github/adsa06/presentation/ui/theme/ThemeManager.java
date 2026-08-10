package io.github.adsa06.presentation.ui.theme;

import java.util.EnumMap;
import java.util.Map;

import com.googlecode.lanterna.graphics.Theme;

import io.github.adsa06.data.repository.JsonRepository;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class ThemeManager {

    public enum ThemeType {
        MINIMALIST
    }

    private Map<ThemeType, Theme> themes = new EnumMap<>(ThemeType.class);

    public ThemeManager(JsonRepository jsonRepository) {
        themes.putAll(jsonRepository.readThemes());
    }

    public Theme getTheme(ThemeType theme) {
        return themes.get(theme);
    }
}
