package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.domain.model.UpgradeEffects.BuildingMultiplierEffect;
import io.github.adsa06.domain.model.UpgradeEffects.ClickBonusEffect;

public class UpgradesManager {
    private GameState state;
    private Map<String, Upgrade> upgrades;
    private BuildingsManager buildingsManager;
    
    private List<String> sessionCompleteUpgrades = new ArrayList<>();

    // private List<Runnable> onChange = new ArrayList<>();

    public UpgradesManager(Map<String, Upgrade> upgrades, GameState state, List<String> completeUpgrades,
            BuildingsManager buildingsManager) {
        this.upgrades = upgrades;
        this.state = state;
        this.buildingsManager = buildingsManager;

        completeUpgrades.forEach(u -> {
            Upgrade upgrade = upgrades.get(u);

            if (upgrade != null) {
                upgrade.setPurchased(true);
                Map<String, Building> buildings = buildingsManager.getBuildingsMap();
                Object object;

                switch (upgrade.getPayload()) {
                    case BuildingMultiplierEffect b -> {
                        object = buildings.get(b.getBuildingId());
                        state.addClicksPerSecond(((Building) object).getBaseProduction()
                                * ((Building) object).getLevel() * (b.getFactor() - 1));
                        ((Building) object).scaleBaseProduction(b.getFactor());
                    }
                    case ClickBonusEffect c -> {
                        object = state;
                        state.incrementCounterPerClick(c.getBonusAmount());
                    }
                };
            }
        });
        buildingsManager.getOnChange().forEach(Runnable::run);
    }

    public boolean checkAndUpdate(Upgrade upgrade) {
        Map<String, Building> buildings = buildingsManager.getBuildingsMap();

        Object object;
        Runnable effect = switch (upgrade.getPayload()) {
            case BuildingMultiplierEffect b -> {
                object = buildings.get(b.getBuildingId());
                yield () -> {
                    state.addClicksPerSecond(((Building) object).getBaseProduction() * ((Building) object).getLevel()
                            * (b.getFactor() - 1));
                    ((Building) object).scaleBaseProduction(b.getFactor());
                };
            }
            case ClickBonusEffect c -> {
                object = state;
                yield () -> state.incrementCounterPerClick(c.getBonusAmount());
            }
        };
        boolean wasPurchased = upgrade.checkAndUpdate(state, object, effect);
        
        if(wasPurchased) {
            sessionCompleteUpgrades.add(upgrade.getId());
            buildingsManager.getOnChange().forEach(Runnable::run);
        }
        return wasPurchased;
    }

    public Collection<Upgrade> getUpgrades() {
        return upgrades.values();
    }

    public List<String> getSessionCompleteUpgrades() {
        return sessionCompleteUpgrades;
    }

    public void setSessionCompleteUpgrades(List<String> sessionCompleteUpgrades) {
        this.sessionCompleteUpgrades = sessionCompleteUpgrades;
    }
}