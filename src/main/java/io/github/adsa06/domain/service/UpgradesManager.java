package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.List;

import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.model.GameUpgrades;

public class UpgradesManager {
    private GameState state;
    private GameUpgrades upgrades;
    private List<String> sessionCompleteUpgrades = new ArrayList<>();

    private List<Runnable> onChange = new ArrayList<>();
    
    public UpgradesManager(GameState state, GameUpgrades upgrades, List<String> sessionCompleteUpgrades) {
        this.state = state;
        this.upgrades = upgrades;

    }
}
