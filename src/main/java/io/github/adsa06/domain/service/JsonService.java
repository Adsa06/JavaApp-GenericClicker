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
import io.github.adsa06.domain.model.UpgradeEffects.BuildingMultiplierEffect;
import io.github.adsa06.domain.model.UpgradeEffects.ClickBonusEffect;
import io.github.adsa06.domain.model.UpgradeEffects.UpgradeEffect;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
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

            String id = obj.get("id").getAsString();
            long cost = obj.get("cost").getAsLong();
            
            JsonObject effect = obj.getAsJsonObject("effect");

            String effectType = effect.get("type").getAsString();
            JsonObject effectPayload = effect.getAsJsonObject("payload");

            UpgradeEffect payload = switch(effectType) {
                case "click" -> new ClickBonusEffect(effectPayload.get("amount").getAsLong());
                case "building" -> new BuildingMultiplierEffect(
                    effectPayload.get("id").getAsString(),
                    effectPayload.get("multiplier").getAsInt()
                );
                default -> throw new IllegalArgumentException("Effect desconocido: " + effectType);
            };

            JsonObject required = obj.getAsJsonObject("required");

            String requiredType = required.get("type").getAsString();
            JsonObject requiredPayload = required.getAsJsonObject("payload");

            Predicate<Object> condition = switch (requiredType) {
                case "points" -> (gameState) -> ((GameState)gameState).getTotalCounter() >= requiredPayload.get("amount").getAsInt();
                case "building" -> (building) -> ((Building)building).getLevel() >= requiredPayload.get("amount").getAsInt();
                default -> throw new IllegalArgumentException("Required desconocido: " + requiredType);
            };

            Upgrade upgrade = new Upgrade(id, id + "Title", id + "Description", cost, payload, condition);
            upgrades.put(id, upgrade);
        }

        return upgrades;
    }
}
