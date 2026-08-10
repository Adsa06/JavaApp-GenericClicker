package io.github.adsa06.data.json.mappers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import io.github.adsa06.data.json.dto.AchievementDTO;
import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonAchievementMapper {
    public Map<String, Achievement> toDomain(Map<String, AchievementDTO> achievementDTO) {
        Map<String, Achievement> achievements = new LinkedHashMap<>();

        achievementDTO.forEach((id, object) -> {
            long required = object.required();
            Predicate<GameState> condition = switch (object.stat()) {
                case "counter" -> (gameState) -> gameState.getCounter() >= required;
                case "clicksPerSecond" -> (gameState) -> gameState.getClicksPerSecond() >= required;
                case "purchasedBuildings" -> (gameState) -> gameState.getPurchasedBuildings() >= required;
                default -> throw new IllegalArgumentException("Stat desconocido: " + object.stat());
            };

            Achievement achievement = new Achievement(id, id + "Title", id + "Description", condition);
            achievements.put(id, achievement);
        });

        return achievements;
    }
}
