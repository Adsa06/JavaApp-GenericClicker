package io.github.adsa06.domain.model.UpgradeRequirements;

import io.github.adsa06.domain.model.Building;

public final class BuildingAmountRequirement extends UpgradeRequirement {
    private String buildingId;
    private long amount;

    public BuildingAmountRequirement(String buildingId, long amount) {
        this.buildingId = buildingId;
        this.amount = amount;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean test(Object building) {
        return ((Building)building).getLevel() >= amount;
    }
}
