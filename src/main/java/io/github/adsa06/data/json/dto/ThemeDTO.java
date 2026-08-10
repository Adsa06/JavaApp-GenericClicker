package io.github.adsa06.data.json.dto;

public record ThemeDTO(String themeType, boolean isBold, ColorDTO baseForeground, ColorDTO baseBackground,
        ColorDTO editableForeground, ColorDTO editableBackground, ColorDTO selectedForeground, ColorDTO selectedBackground,
        ColorDTO guiBackground) {
        public record ColorDTO(int r, int g, int b){}
}
