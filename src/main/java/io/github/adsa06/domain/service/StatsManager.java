package io.github.adsa06.domain.service;

import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class StatsManager {
    private final GameState state;

    public StatsManager(GameState state) {
        this.state = state;
    }

    public void addListener(Runnable update) {
        state.addListener(update);
    }

    public long getTotalCounter() {
        return state.getTotalCounter();
    }

    public long getClicksPerSecond() {
        return state.getClicksPerSecond();
    }

    public long getCounterPerClick() {
        return state.getCounterPerClick();
    }

    public long getTotalPurchasedBuildings() {
        return state.getTotalPurchasedBuildings();
    }
}
