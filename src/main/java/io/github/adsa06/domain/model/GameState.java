package io.github.adsa06.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class GameState {
    private AtomicLong totalCounter;
    private AtomicLong counter;
    private AtomicLong clicksPerSecond;
    private long counterPerClick = 10;
    private Map<String, Integer> buildingCounts;

    private List<Runnable> onChange = new ArrayList<>();

    public GameState(long counter, long totalCounter) {
        this.counter = new AtomicLong(counter);
        this.totalCounter = new AtomicLong(totalCounter);
        this.clicksPerSecond = new AtomicLong(0);
        buildingCounts = new HashMap<>();
    }

    public void addListener(Runnable callback) {
        onChange.add(callback);
    }

    public void removeListener(Runnable callback) {
        onChange.remove(callback);
    }

    public void addCounter(long amount) {
        counter.addAndGet(amount);
        totalCounter.addAndGet(amount);
        onChange.forEach(Runnable::run);
    }

    public void removeCounter(long amount) {
        counter.addAndGet(-amount);
        onChange.forEach(Runnable::run);
    }

    public void addClicksPerSecond(long amount) {
        clicksPerSecond.addAndGet(amount);
    }

    public long getCounter() {
        return counter.get();
    }

    public long getTotalCounter() {
        return totalCounter.get();
    }

    public long getClicksPerSecond() {
        return clicksPerSecond.get();
    }

    public long getTotalPurchasedBuildings() {
        return buildingCounts.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public long getPurchasedBuildings(String buildingId) {
        return buildingCounts.getOrDefault(buildingId, 0);
    }

    public long getCounterPerClick() {
        return counterPerClick;
    }

    public void incrementPurchasedBuildings(String buildingId, int num) {
        buildingCounts.put(buildingId, buildingCounts.getOrDefault(buildingId, 0) + num);
        onChange.forEach(Runnable::run);
    }

    public void incrementPurchasedBuildings(String buildingId) {
        incrementPurchasedBuildings(buildingId, 1);
    }

    public void doClick() {
        addCounter(counterPerClick);
    }

    public void incrementCounterPerClick(long counterPerClick) {
        this.counterPerClick += counterPerClick;
    }
}