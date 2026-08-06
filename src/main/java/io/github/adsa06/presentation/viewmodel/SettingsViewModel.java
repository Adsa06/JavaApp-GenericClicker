package io.github.adsa06.presentation.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;

import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.data.repository.SettingsRepository;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.service.AchievementManager;
import io.github.adsa06.domain.service.BuildingsManager;
import io.github.adsa06.domain.service.UpgradesManager;
import io.github.adsa06.presentation.ui.translations.TranslationManager;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class SettingsViewModel {
    private final SettingsRepository settingsRepository;
    private final GameRepository gameRepository;
    private final GameState gameState;
    private final AchievementManager achievementManager;
    private final BuildingsManager buildingsManager;
    private final UpgradesManager upgradesManager;
    private final TranslationManager translationManager;

    private Runnable saveDone;

    public SettingsViewModel(TranslationManager translationManager, SettingsRepository settingsRepository,
            GameRepository gameRepository, GameState gameState, AchievementManager achievementManager, BuildingsManager buildingsManager, UpgradesManager upgradesManager) {
        this.translationManager = translationManager;
        this.settingsRepository = settingsRepository;
        this.gameRepository = gameRepository;
        this.gameState = gameState;
        this.achievementManager = achievementManager;
        this.buildingsManager = buildingsManager;
        this.upgradesManager = upgradesManager;
    }

    public void save() {
        gameRepository.saveAchievements(achievementManager.getSessionCompleteAchievements());
        achievementManager.setSessionCompleteAchievements(new ArrayList<>());

        gameRepository.saveBuildings(buildingsManager.getSessionCompleteBuildings());
        buildingsManager.setSessionCompleteBuildings(new HashSet<>());      

        gameRepository.saveUpgrades(upgradesManager.getSessionCompleteUpgrades());
        upgradesManager.setSessionCompleteUpgrades(new ArrayList<>());

        gameRepository.updateStats(gameState);
        settingsRepository.updateSettings(translationManager.getLocale());
        saveDone.run();
    }

    public void setListener(Runnable saveDone) {
        this.saveDone = saveDone;
    }
}
