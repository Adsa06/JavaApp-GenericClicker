package io.github.adsa06.presentation.viewmodel;

import java.util.Collection;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.service.BuildingsManager;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class BuildingViewModel {
    
    private final BuildingsManager buildingsManager;

    public BuildingViewModel(BuildingsManager buildingsManager) {
        this.buildingsManager = buildingsManager;
    }

    public boolean buyBuilding(Building building) {
        return buildingsManager.buyBuilding(building);
    }
    public Collection<Building> getBuildings() {
        return buildingsManager.getBuildings();
    }

    public void addListener(Runnable callback) {
        buildingsManager.addListener(callback);
    }
}
