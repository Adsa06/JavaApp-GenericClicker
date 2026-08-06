package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.domain.model.UpgradeEffects.BuildingMultiplierEffect;
import io.github.adsa06.domain.model.UpgradeEffects.ClickBonusEffect;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class UpgradesManager {
    private final GameState state;
    private Map<String, Upgrade> upgrades;
    private final BuildingsManager buildingsManager;

    private List<String> sessionCompleteUpgrades = new ArrayList<>();
    private Runnable refreshUi;

    public UpgradesManager(JsonService jsonService, GameState state, GameRepository gameRepository,
            BuildingsManager buildingsManager) {
        this.upgrades = jsonService.readUpgrades();
        this.state = state;
        this.buildingsManager = buildingsManager;

        gameRepository.findAllUpgrades().forEach(u -> {
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
                }
                ;
            }
        });
        buildingsManager.getOnChange().forEach(Runnable::run);

        state.addListener(() -> {
            upgrades.values().forEach(u -> {
                Map<String, Building> buildings = buildingsManager.getBuildingsMap();

                Object object = switch (u.getPayload()) {
                    case BuildingMultiplierEffect b -> buildings.get(b.getBuildingId());
                    case ClickBonusEffect c -> state;
                };
                if(u.runCondition(object)) refreshUi.run();
            });
        });
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

        if (wasPurchased) {
            sessionCompleteUpgrades.add(upgrade.getId());
            buildingsManager.getOnChange().forEach(Runnable::run);
        }
        return wasPurchased;
    }

    public Collection<Upgrade> getFilterUpgrades() {
        Map<String, Building> buildings = buildingsManager.getBuildingsMap();

        return upgrades.values().stream().filter(u -> {
            Object object = switch (u.getPayload()) {
                case BuildingMultiplierEffect b -> buildings.get(b.getBuildingId());
                case ClickBonusEffect c -> state;
            };
            return u.isConditionComplete(object);
        }).toList();
    }

    public List<String> getSessionCompleteUpgrades() {
        return sessionCompleteUpgrades;
    }

    public void setSessionCompleteUpgrades(List<String> sessionCompleteUpgrades) {
        this.sessionCompleteUpgrades = sessionCompleteUpgrades;
    }

    public void addListener(Runnable refreshUi) {
        this.refreshUi = refreshUi;
    }
}