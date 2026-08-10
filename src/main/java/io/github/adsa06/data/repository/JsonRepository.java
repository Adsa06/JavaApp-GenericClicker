package io.github.adsa06.data.repository;

import java.util.Map;

import com.googlecode.lanterna.graphics.Theme;

import io.github.adsa06.data.json.JsonDataSource;
import io.github.adsa06.data.json.mappers.JsonAchievementMapper;
import io.github.adsa06.data.json.mappers.JsonBuildingMapper;
import io.github.adsa06.data.json.mappers.JsonUpgradeMapper;
import io.github.adsa06.data.json.mappers.JsonThemeMapper;
import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.presentation.ui.theme.ThemeManager.ThemeType;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonRepository {

    private final JsonDataSource jsonDataSource;
    private final JsonAchievementMapper jsonAchievementMapper;
    private final JsonBuildingMapper jsonBuildingMapper;
    private final JsonUpgradeMapper jsonUpgradeMapper;
    private final JsonThemeMapper jsonThemeMapper;

    public JsonRepository(JsonDataSource jsonDataSource, JsonAchievementMapper jsonAchievementMapper,
            JsonBuildingMapper jsonBuildingMapper,
            JsonUpgradeMapper jsonUpgradeMapper,
            JsonThemeMapper jsonThemeMapper) {
        this.jsonDataSource = jsonDataSource;
        this.jsonAchievementMapper = jsonAchievementMapper;
        this.jsonBuildingMapper = jsonBuildingMapper;
        this.jsonUpgradeMapper = jsonUpgradeMapper;
        this.jsonThemeMapper = jsonThemeMapper;
    }

    public Map<String, Achievement> readAchievements() {
        return jsonAchievementMapper.toDomain(jsonDataSource.readAchievements());
    }

    public Map<String, Building> readBuildings() {
        return jsonBuildingMapper.toDomain(jsonDataSource.readBuildings());
    }

    public Map<String, Upgrade> readUpgrades() {
        return jsonUpgradeMapper.toDomain(jsonDataSource.readUpgrades());
    }

    public Map<ThemeType, Theme> readThemes() {
        return jsonThemeMapper.toDomain(jsonDataSource.readThemes());
    }
}
