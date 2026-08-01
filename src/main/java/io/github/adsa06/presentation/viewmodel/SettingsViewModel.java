package io.github.adsa06.presentation.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;

import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.data.repository.SettingsRepository;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.service.AchievementManager;
import io.github.adsa06.domain.service.BuildingsManager;
import io.github.adsa06.presentation.ui.translations.TranslationManager;

public class SettingsViewModel {
    private SettingsRepository settingsRepository;
    private GameRepository gameRepository;
    private GameState gameState;
    private AchievementManager achievementManager;
    private BuildingsManager buildingsManager;
    private TranslationManager translationManager;

    private Runnable saveDone;

    public SettingsViewModel(TranslationManager translationManager, SettingsRepository settingsRepository,
            GameRepository gameRepository, GameState gameState, AchievementManager achievementManager, BuildingsManager buildingsManager) {
        this.translationManager = translationManager;
        this.settingsRepository = settingsRepository;
        this.gameRepository = gameRepository;
        this.gameState = gameState;
        this.achievementManager = achievementManager;
        this.buildingsManager = buildingsManager;
    }

    public void save() {
        gameRepository.saveAchievements(achievementManager.getSessionCompleteAchievements());
        achievementManager.setSessionCompleteAchievements(new ArrayList<>());
        
        gameRepository.saveBuildings(buildingsManager.getSessionCompleteBuildings());
        buildingsManager.setSessionCompleteBuildings(new HashSet<>());

        gameRepository.updateStats(gameState);
        settingsRepository.updateSettings(translationManager.getLocale());
        saveDone.run();
    }

    public void setListener(Runnable saveDone) {
        this.saveDone = saveDone;
    }
}
