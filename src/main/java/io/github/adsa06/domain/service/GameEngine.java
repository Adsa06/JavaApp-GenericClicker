package io.github.adsa06.domain.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class GameEngine {
    private final GameState state;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public GameEngine(GameState state) {
        this.state = state;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            state.addCounter(state.getClicksPerSecond());
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}