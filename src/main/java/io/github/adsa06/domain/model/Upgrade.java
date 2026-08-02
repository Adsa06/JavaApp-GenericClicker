package io.github.adsa06.domain.model;

import java.util.function.Predicate;

import io.github.adsa06.domain.model.UpgradeEffects.UpgradeEffect;

public class Upgrade {
    private String id;
    private String titleId;
    private String descripcionId;
    private long cost;
    private UpgradeEffect payload;
    private Predicate<Object> condition;
    private boolean conditionComplete = false;
    private boolean purchased = false;

    public Upgrade(String id, String titleId, String descripcionId, long cost, UpgradeEffect payload, Predicate<Object> condition) {
        this.id = id;
        this.titleId = titleId;
        this.descripcionId = descripcionId;
        this.cost = cost;
        this.payload = payload;
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

    public UpgradeEffect getPayload() {
        return payload;
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
