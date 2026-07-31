package io.github.adsa06.domain.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.model.Upgrade;

public class JsonService {

    public Map<String, Achievement> readAchievements() {
        Map<String, Achievement> achievements = new LinkedHashMap<>();

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("data/achievements.json");

        Reader reader = new InputStreamReader(is);

        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();

            String id = obj.get("id").getAsString();
            String stat = obj.get("stat").getAsString();
            long required = obj.get("required").getAsLong();

            Predicate<GameState> condition = switch (stat) {
                case "counter" -> (gameState) -> gameState.getCounter() >= required;
                case "clicksPerSecond" -> (gameState) -> gameState.getClicksPerSecond() >= required;
                case "purchasedBuildings" -> (gameState) -> gameState.getPurchasedBuildings() >= required;
                default -> throw new IllegalArgumentException("Stat desconocido: " + stat);
            };

            Achievement achievement = new Achievement(id, id + "Title", id + "Description", condition);
            achievements.put(id, achievement);
        }

        return achievements;
    }

    public Map<String, Building> readBuildings() {
        Map<String, Building> buildings = new LinkedHashMap<>();

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("data/buildings.json");

        Reader reader = new InputStreamReader(is);

        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();

            String id = obj.get("id").getAsString();
            long cost = obj.get("baseCost").getAsLong();
            int baseProduction = obj.get("baseProduction").getAsInt();

            Building builing = new Building(id, id + "Title", id + "Description", cost, baseProduction);
            buildings.put(id, builing);
        }

        return buildings;
    }

    public Map<String, Upgrade> readUpgrades() {
        Map<String, Upgrade> upgrades = new LinkedHashMap<>();

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("data/upgrades.json");

        Reader reader = new InputStreamReader(is);

        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();

        }

        return upgrades;
    }
}
