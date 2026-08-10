package io.github.adsa06.data.json;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.github.adsa06.data.json.deserializer.EffectDeserializer;
import io.github.adsa06.data.json.deserializer.RequirementDeserializer;
import io.github.adsa06.data.json.dto.AchievementDTO;
import io.github.adsa06.data.json.dto.BuildingDTO;
import io.github.adsa06.data.json.dto.ThemeDTO;
import io.github.adsa06.data.json.dto.UpgradeDTO;
import io.github.adsa06.data.json.dto.UpgradeDTO.Effect;
import io.github.adsa06.data.json.dto.UpgradeDTO.Requirement;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonDataSource {
    public LinkedHashMap<String, AchievementDTO> readAchievements() {
        LinkedHashMap<String, AchievementDTO> achievements = new LinkedHashMap<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/achievements.json")) {

            if(is == null)
                throw new IllegalArgumentException("No existe el achievements.json");

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type type = new TypeToken<LinkedHashMap<String, AchievementDTO>>(){}.getType();

            achievements = gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return achievements;
    }

    public LinkedHashMap<String, BuildingDTO> readBuildings() {
        LinkedHashMap<String, BuildingDTO> buildings = new LinkedHashMap<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/buildings.json")) {

            if(is == null)
                throw new IllegalArgumentException("No existe el achievements.json");

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type type = new TypeToken<LinkedHashMap<String, BuildingDTO>>(){}.getType();

            buildings = gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buildings;
    }

    public LinkedHashMap<String, UpgradeDTO> readUpgrades() {
        LinkedHashMap<String, UpgradeDTO> upgrades = new LinkedHashMap<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/upgrades.json")) {
            
            if(is == null)
                throw new IllegalArgumentException("No existe el achievements.json");

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Requirement.class, new RequirementDeserializer())
                    .registerTypeAdapter(Effect.class, new EffectDeserializer())
                    .create();

            Type type = new TypeToken<LinkedHashMap<String, UpgradeDTO>>(){}.getType();

            upgrades = gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return upgrades;
    }

    public Map<String, ThemeDTO> readThemes() {
        Map<String, ThemeDTO> themes = new HashMap<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/themes.json")) {

            if(is == null)
                throw new IllegalArgumentException("No existe el achievements.json");

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, ThemeDTO>>(){}.getType();

            themes = gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return themes;
    }
}
