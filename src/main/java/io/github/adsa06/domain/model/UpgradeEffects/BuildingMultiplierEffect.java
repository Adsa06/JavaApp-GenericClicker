package io.github.adsa06.domain.model.UpgradeEffects;

public final class BuildingMultiplierEffect extends UpgradeEffect {
    private String buildingId;
    private int factor;

    public BuildingMultiplierEffect(String buildingId, int factor) {
        this.buildingId = buildingId;
        this.factor = factor;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public int getFactor() {
        return factor;
    }
}