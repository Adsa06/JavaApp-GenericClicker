package io.github.adsa06.domain.model.UpgradeRequirements;

import io.github.adsa06.domain.model.GameState;

public final class ClickAmountRequirement extends UpgradeRequirement {
    private long amount;

    public ClickAmountRequirement(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean test(Object gameState) {
        return ((GameState)gameState).getTotalCounter() >= amount;
    }
}
