package io.github.adsa06.domain.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;

public class BuildingsManager {
    private GameState state;
    private Map<String, Building> buildings;
    private Set<Building> sessionCompleteBuildings = new HashSet<>();
    
    public BuildingsManager(Map<String, Building> buildings, GameState state, List<Building> completeBuildings) {
        this.state = state;
        this.buildings = buildings;

        completeBuildings.forEach(b -> {
            Building building = buildings.get(b.getId());
            if (building != null) {
                building.setCost(b.getCost());
                building.setLevel(b.getLevel());
            }
            state.addClicksPerSecond(building.getBaseProduction()*building.getLevel());
            state.incrementPurchasedBuildings(building.getLevel());
        });
    }

    public boolean buyBuilding(Building building) {
            boolean wasPurchased = building.buyAndUpdate(state);

            if(wasPurchased) {
                sessionCompleteBuildings.add(building);
                state.incrementPurchasedBuildings();
            }

            return wasPurchased;
    }

    public Collection<Building> getBuildings() {
        return buildings.values();
    }

    public Set<Building> getSessionCompleteBuildings() {
        return sessionCompleteBuildings;
    }

    public void setSessionCompleteBuildings(Set<Building> sessionCompleteBuildings) {
        this.sessionCompleteBuildings = sessionCompleteBuildings;
    }
}