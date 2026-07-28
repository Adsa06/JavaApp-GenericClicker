package io.github.adsa06.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AchievementManager {

    private GameState state;
    private GameAchievements achievements;

    private List<Runnable> onChange = new ArrayList<>();;

    public AchievementManager(GameState state, GameAchievements achievements) {
        this.state = state;
        this.achievements = achievements;

        // se reevalúa cada vez que el GameState notifica ALGÚN cambio
        state.addListener(this::onStateChanged);
    }

    private void onStateChanged() {
        for (Achievement achievement : achievements.getAchievements()) {
            if (achievement.checkAndUpdate(state)) {
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
        return achievements.getAchievements();
    }
}