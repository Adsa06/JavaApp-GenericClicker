package io.github.adsa06.data.json.mappers;

import java.util.EnumMap;
import java.util.Map;

import com.googlecode.lanterna.TextColor.RGB;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;

import io.github.adsa06.data.json.dto.ThemeDTO;
import io.github.adsa06.data.json.dto.ThemeDTO.ColorDTO;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonThemeMapper {

    public Map<ThemeType, Theme> toDomain(Map<String, ThemeDTO> themesDTO) {
        Map<ThemeType, Theme> themes = new EnumMap<>(ThemeType.class);

        themesDTO.forEach((id, object) -> {
            ThemeType themeType = ThemeType.valueOf(id);

            Theme theme = SimpleTheme.makeTheme(
                    object.isBold(),
                    parseRGB(object.baseForeground()),
                    parseRGB(object.baseBackground()),
                    parseRGB(object.editableForeground()),
                    parseRGB(object.editableBackground()),
                    parseRGB(object.selectedForeground()),
                    parseRGB(object.selectedBackground()),
                    parseRGB(object.guiBackground())
                );
            themes.put(themeType, theme);
        });

        return themes;

    }

    private RGB parseRGB(ColorDTO color) {
        return new RGB(
            color.r(),
            color.g(),
            color.b()
        );
    }

}
