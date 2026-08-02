package io.github.adsa06.presentation.viewmodel;

import io.github.adsa06.domain.service.StatsManager;

public class StatsViewModel {
    private StatsManager statsManager;

    public StatsViewModel(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public void addListener(Runnable update) {
        statsManager.addListener(update);
    }

    public long getTotalCounter() {
        return statsManager.getTotalCounter();
    }

    public long getClicksPerSecond() {
        return statsManager.getClicksPerSecond();
    }

    public long getCounterPerClick() {
        return statsManager.getCounterPerClick();
    }

}
