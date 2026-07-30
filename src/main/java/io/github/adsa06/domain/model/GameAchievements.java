package io.github.adsa06.domain.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameAchievements {

    private Map<String, Achievement> achievements = new LinkedHashMap<>();

    public GameAchievements() {
        achievements.put("firstContact", new Achievement(
                "firstContact", "firstContactTitle", "firstContactDescription",
                state -> state.getCounter() >= 1));

        achievements.put("clickstorm", new Achievement(
                "clickstorm", "clickstormTitle", "clickstormDescription",
                state -> state.getCounter() >= 100));

        achievements.put("dataHoarder", new Achievement(
                "dataHoarder", "dataHoarderTitle", "dataHoarderDescription",
                state -> state.getCounter() >= 1000));

        achievements.put("infrastructure", new Achievement(
                "infrastructure", "infrastructureTitle", "infrastructureDescription",
                state -> state.getUnlockedUpgrades() >= 10));

        achievements.put("neuralNetwork", new Achievement(
                "neuralNetwork", "neuralNetworkTitle", "neuralNetworkDescription",
                state -> state.getClicksPerSecond() >= 10));

        achievements.put("megacluster", new Achievement(
                "megacluster", "megaclusterTitle", "megaclusterDescription",
                state -> state.getCounter() >= 1000000));

        achievements.put("gridOperator", new Achievement(
                "gridOperator", "gridOperatorTitle", "gridOperatorDescription",
                state -> state.getUnlockedUpgrades() >= 50));

        achievements.put("singularity", new Achievement(
                "singularity", "singularityTitle", "singularityDescription",
                state -> state.getClicksPerSecond() >= 1000));

    }

    public Collection<Achievement> getAchievements() {
        return achievements.values();
    }

    public Map<String, Achievement> getAchievementsMap() {
        return achievements;
    }
}