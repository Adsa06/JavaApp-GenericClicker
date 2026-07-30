package io.github.adsa06.presentation.viewmodel;

import java.util.ArrayList;

import io.github.adsa06.data.repository.Repository;
import io.github.adsa06.domain.model.AchievementManager;
import io.github.adsa06.domain.model.GameState;

public class SettingsViewModel {
    private Repository repository;
    private GameState gameState;
    private AchievementManager achievementManager;

    private Runnable saveDone;

    public SettingsViewModel(Repository repository, GameState gameState, AchievementManager achievementManager) {
        this.repository = repository;
        this.gameState = gameState;
        this.achievementManager = achievementManager;
    }

    public void save() {
        repository.saveAchievements(achievementManager.getSessionCompleteAchievements());
        achievementManager.setSessionCompleteAchievements(new ArrayList<>());

        repository.updateStats(gameState);
        saveDone.run();
    }

    public void setListener(Runnable saveDone) {
        this.saveDone = saveDone;
    }
}
