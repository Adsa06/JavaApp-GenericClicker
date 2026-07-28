package io.github.adsa06.domain.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameAchievements {

    private Map<String, Achievement> achievements = new LinkedHashMap<>();

    public GameAchievements() {
        achievements.put("firstContact", new Achievement(
            "firstContact", "firstContactTitle", "firstContactDescription",
            state -> state.getCounter() >= 1
        ));
        achievements.put("clickstorm", new Achievement(
            "clickstorm", "clickstormTitle", "clickstormDescription",
            state -> state.getCounter() >= 100
        ));
    }

    public Collection<Achievement> getAchievements() {
        return achievements.values();
    }
}