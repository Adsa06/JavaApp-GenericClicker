package io.github.adsa06.presentation.viewmodel;

import io.github.adsa06.domain.service.StatsManager;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class StatsViewModel {
    private final StatsManager statsManager;

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

    public long getTotalPurchasedBuildings() {
        return statsManager.getTotalPurchasedBuildings();
    }
}
