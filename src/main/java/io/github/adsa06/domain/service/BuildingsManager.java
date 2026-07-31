package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;

public class BuildingsManager {
    private GameState state;
    private Map<String, Building> buildings;
    private List<String> sessionCompleteBuildings = new ArrayList<>();
    
    public BuildingsManager(Map<String, Building> buildings, GameState state, List<Building> completeBuildings) {
        this.state = state;
        this.buildings = buildings;

        completeBuildings.forEach(b -> {
            Building building = buildings.get(b.getId());

            if (building != null) {
                building.setCost(b.getCost());
                building.setLevel(b.getLevel());
            }
        });
    }

    public void buyBuilding(Building building) {
        building.buyAndUpdate(state);
    }

    public Collection<Building> getBuildings() {
        return buildings.values();
    }

    public List<String> getSessionCompleteBuildings() {
        return sessionCompleteBuildings;
    }

    public void setSessionCompleteBuildings(List<String> sessionCompleteBuildings) {
        this.sessionCompleteBuildings = sessionCompleteBuildings;
    }
}