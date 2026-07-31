package io.github.adsa06.domain.model;

public class Building {
    private String id;
    private String titleId;
    private String descripcionId;
    private int level = 0;
    private int cost;
    private int baseProduction;

    public Building(String id, String titleId, String descripcionId, int cost, int baseProduction) {
        this.id = id;
        this.titleId = titleId;
        this.descripcionId = descripcionId;
        this.cost = cost;
        this.baseProduction = baseProduction;
    }

    public boolean buyAndUpdate(GameState state) {
        boolean canBuy = state.getCounter() >= cost;
        if (canBuy) {
            state.addCounter(-cost);
            state.addClicksPerSecond(baseProduction);
            level++;
            cost *= 1.15f;
        }
        return canBuy;
    }

    public String getId() {
        return id;
    }

    public String getTitleId() {
        return titleId;
    }

    public String getDescripcionId() {
        return descripcionId;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public int getBaseProduction() {
        return baseProduction;
    }
}
