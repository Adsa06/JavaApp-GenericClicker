package io.github.adsa06.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GameState {
    private AtomicLong counter = new AtomicLong(0);
    private long clicksPerSecond = 0;

    private List<Runnable> onChange = new ArrayList<>();;

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
        clicksPerSecond += amount;
        // No creo que haga falta
        //onChange.forEach(Runnable::run);
    }

    public long getCounter() {
        return counter.get();
    }

    public long getClicksPerSecond() {
        return clicksPerSecond;
    }
}