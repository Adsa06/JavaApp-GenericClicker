package io.github.adsa06.domain.model;

public class Building {
    private String id;
    private String titleId;
    private String descripcionId;
    private int level = 0;
    private long cost;
    private int baseProduction;

    public Building(String id, String titleId, String descripcionId, long cost, int baseProduction) {
        this.id = id;
        this.titleId = titleId;
        this.descripcionId = descripcionId;
        this.cost = cost;
        this.baseProduction = baseProduction;
    }

    public Building(String id, int level, long cost) {
        this.id = id;
        this.level = level;
        this.cost = cost;
    }

    public boolean buyAndUpdate(GameState state) {
        boolean canBuy = state.getCounter() >= cost;
        if (canBuy) {
            state.removeCounter(cost);
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

    public long getCost() {
        return cost;
    }

    public int getBaseProduction() {
        return baseProduction;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}
