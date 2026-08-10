package io.github.adsa06.domain.model;

import io.github.adsa06.domain.model.UpgradeEffects.UpgradeEffect;
import io.github.adsa06.domain.model.UpgradeRequirements.UpgradeRequirement;

public class Upgrade {
    private String id;
    private String titleId;
    private String descripcionId;
    private long cost;
    private UpgradeEffect effect;
    private UpgradeRequirement condition;
    private boolean conditionComplete = false;
    private boolean purchased = false;

    public Upgrade(String id, String titleId, String descripcionId, long cost, UpgradeEffect effect, UpgradeRequirement condition) {
        this.id = id;
        this.titleId = titleId;
        this.descripcionId = descripcionId;
        this.cost = cost;
        this.effect = effect;
        this.condition = condition;
    }

    public boolean checkAndUpdate(GameState state, Object object, Runnable effect) {
        boolean unlocked = !purchased && state.getCounter() >= cost && condition.test(object);
        if (unlocked) {
            conditionComplete = true;
            state.removeCounter(cost);
            effect.run();
            purchased = true;
        }
        return unlocked;
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

    public UpgradeEffect getEffect() {
        return effect;
    }
    
    public UpgradeRequirement getCondition() {
        return condition;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public long getCost() {
        return cost;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean runCondition(Object object) {
        boolean unlocked = !conditionComplete && condition.test(object);
        if(unlocked) conditionComplete = true;
        return unlocked;
    }

    public boolean isConditionComplete(Object object) {
        boolean unlocked = conditionComplete || condition.test(object);
        if(unlocked) conditionComplete = true;
        return unlocked;
    }
}
