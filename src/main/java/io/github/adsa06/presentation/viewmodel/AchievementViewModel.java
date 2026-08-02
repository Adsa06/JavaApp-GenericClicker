package io.github.adsa06.presentation.viewmodel;

import java.util.Collection;

import io.github.adsa06.domain.model.Achievement;
import io.github.adsa06.domain.service.AchievementManager;

public class AchievementViewModel {
    
    private AchievementManager achievementManager;

    public AchievementViewModel(AchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }

    public void addListener(Runnable callback) {
        achievementManager.addListener(callback);
    }

    public Collection<Achievement> getAchievements() {
        return achievementManager.getAchievements();
    }
}
