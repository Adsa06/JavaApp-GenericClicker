package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.List;

import io.github.adsa06.domain.model.GameState;

public class BuildingsManager {
    private GameState state;
    private List<String> sessionCompleteBuildings = new ArrayList<>();

    private List<Runnable> onChange = new ArrayList<>();
    
    public BuildingsManager(GameState state, List<String> sessionCompleteUpgrades) {
        this.state = state;
    }
}