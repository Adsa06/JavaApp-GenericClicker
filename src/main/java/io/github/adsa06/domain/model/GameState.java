package io.github.adsa06.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GameState {
    private AtomicLong counter;
    private AtomicLong clicksPerSecond;
    private Long counterPerClicks;
    private long purchasedBuildings;

    private List<Runnable> onChange = new ArrayList<>();

    public GameState(long counter, long clicksPerSecond, long purchasedBuildings) {
        this.counter = new AtomicLong(counter);
        this.clicksPerSecond = new AtomicLong(clicksPerSecond);
        this.purchasedBuildings = purchasedBuildings;
    }

    public void addListener(Runnable callback) {
        onChange.add(callback);
    }

    public void removeListener(Runnable callback) {
        onChange.remove(callback);
    }

    public void addCounter(long amount) {
        counter.addAndGet(amount);
        onChange.forEach(Runnable::run);
    }

    public void addClicksPerSecond(long amount) {
        clicksPerSecond.addAndGet(amount);
    }

    public long getCounter() {
        return counter.get();
    }

    public long getClicksPerSecond() {
        return clicksPerSecond.get();
    }

    public long getPurchasedBuildings() {
        return purchasedBuildings;
    }

    public void incrementPurchasedBuildings() {
        purchasedBuildings++;
    }
}