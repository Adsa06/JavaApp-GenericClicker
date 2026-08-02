package io.github.adsa06.domain.model.UpgradeEffects;

public final class ClickBonusEffect extends UpgradeEffect {
    private long bonusAmount;

    public ClickBonusEffect(long bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public long getBonusAmount() {
        return bonusAmount;
    }
}