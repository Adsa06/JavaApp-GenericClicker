package io.github.adsa06.presentation.viewmodel;

import java.util.Collection;

import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.domain.service.UpgradesManager;

public class UpgradeViewModel {
    
    private UpgradesManager upgradesManager;

    public UpgradeViewModel(UpgradesManager upgradesManager) {
        this.upgradesManager = upgradesManager;
    }

    public boolean buyUpgrade(Upgrade upgrade) {
        return upgradesManager.checkAndUpdate(upgrade);
    }

    public Collection<Upgrade> getUpgrades() {
        return upgradesManager.getFilterUpgrades();
    }

    public void addListener(Runnable refreshUi) {
        upgradesManager.addListener(refreshUi);
    }
}
