package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.domain.model.GameState;

public class AchievementManager {

    private GameState state;
    private Map<String, Achievement> achievements;

    private List<String> sessionCompleteAchievements = new ArrayList<>();

    private List<Runnable> onChange = new ArrayList<>();



    public AchievementManager(Map<String, Achievement> achievements, GameState state, List<String> completeAchievements) {
        this.state = state;
        this.achievements = achievements;

        completeAchievements.forEach(a -> {
            Achievement achievement = achievements.get(a);

            if (achievement != null) {
                achievement.setFinished(true);
            }
        });

        // se reevalúa cada vez que el GameState notifica ALGÚN cambio
        state.addListener(this::onStateChanged);
    }

    private void onStateChanged() {
        for (Achievement achievement : achievements.values()) {
            if (achievement.checkAndUpdate(state)) {
                sessionCompleteAchievements.add(achievement.getId());
                onChange.forEach(Runnable::run);
            }
        }
    }

    public void addListener(Runnable callback) {
        onChange.add(callback);
    }

    public void removeListener(Runnable callback) {
        onChange.remove(callback);
    }

    public Collection<Achievement> getAchievements() {
        return achievements.values();
    }

    public List<String> getSessionCompleteAchievements() {
        return sessionCompleteAchievements;
    }

    public void setSessionCompleteAchievements(List<String> sessionCompleteAchievements) {
        this.sessionCompleteAchievements = sessionCompleteAchievements;
    }
}