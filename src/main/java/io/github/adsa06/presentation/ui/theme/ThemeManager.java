package io.github.adsa06.presentation.ui.theme;

import java.util.EnumMap;
import java.util.Map;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;

public class ThemeManager {

    public ThemeManager() {

        // Minimalist
        Theme minimalistTheme = SimpleTheme.makeTheme(
                false,
                TextColor.ANSI.WHITE, // Color de texto normal
                TextColor.ANSI.BLACK, // Color de fondo normal
                TextColor.ANSI.WHITE, // Texto cuando está activo/enfocado
                TextColor.ANSI.BLACK, // Fondo cuando está activo (invertido o sutil)
                TextColor.ANSI.BLACK, // PREGUNTAR/OTRO
                TextColor.ANSI.WHITE, // Borde o detalles
                TextColor.ANSI.BLACK // Escritorio de fondo
        );
        themes.put(ThemeType.MINIMALIST, minimalistTheme);
    }

    public enum ThemeType {
        MINIMALIST
    }

    private Map<ThemeType , Theme> themes = new EnumMap<>(ThemeType.class);

    public Theme getTheme(ThemeType theme) {
        return themes.get(theme);
    }
}
