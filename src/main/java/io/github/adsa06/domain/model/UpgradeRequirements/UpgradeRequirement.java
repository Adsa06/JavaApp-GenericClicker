package io.github.adsa06.domain.model.UpgradeRequirements;

public abstract sealed class UpgradeRequirement permits BuildingAmountRequirement, ClickAmountRequirement {
    public abstract boolean test(Object object);
}
