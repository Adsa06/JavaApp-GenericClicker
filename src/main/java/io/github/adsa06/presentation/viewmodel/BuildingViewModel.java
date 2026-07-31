package io.github.adsa06.presentation.viewmodel;

import java.util.Collection;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.service.BuildingsManager;

public class BuildingViewModel {
    
    private BuildingsManager buildingsManager;

    public BuildingViewModel(BuildingsManager buildingsManager) {
        this.buildingsManager = buildingsManager;
    }

    public Collection<Building> getBuildings() {
        return buildingsManager.getBuildings();
    }
}
