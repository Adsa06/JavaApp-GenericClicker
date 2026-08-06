package io.github.adsa06.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.adsa06.data.repository.GameRepository;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class BuildingsManager {
    private final GameState state;
    private Map<String, Building> buildings;
    private Set<Building> sessionCompleteBuildings = new HashSet<>();

    private List<Runnable> onChange = new ArrayList<>();
    
    public BuildingsManager(JsonService jsonService, GameState state, GameRepository gameRepository) {
        this.state = state;
        this.buildings = jsonService.readBuildings();

        gameRepository.findAllBuildings().forEach(b -> {
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

    public Map<String, Building> getBuildingsMap() {
        return buildings;
    }

    public void addListener(Runnable callback) {
        onChange.add(callback);
    }

    public List<Runnable> getOnChange() {
        return onChange;
    }
}