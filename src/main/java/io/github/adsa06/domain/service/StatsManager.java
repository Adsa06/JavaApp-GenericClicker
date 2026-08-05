package io.github.adsa06.domain.service;

import io.github.adsa06.domain.model.GameState;

public class StatsManager {
    private GameState state;

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
}
