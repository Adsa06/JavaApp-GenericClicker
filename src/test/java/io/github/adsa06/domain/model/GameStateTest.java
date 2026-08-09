package io.github.adsa06.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    private GameState state;

    @BeforeEach
    void setUp() {
        state = new GameState(0, 0);
    }

    @Test
    void shouldIncreaseCounterAndTotalCounterWhenAddCounter() {
        state.addCounter(5);

        assertEquals(5, state.getCounter());
        assertEquals(5, state.getTotalCounter());
    }

    @Test
    void shouldDecreaseCounterWhenRemoveCounter() {
        state.addCounter(10);
        state.removeCounter(3);

        assertEquals(7, state.getCounter());
    }

    @Test
    void shouldFireListenersWhenStateChanges() {
        boolean[] triggered = { false };
        state.addListener(() -> triggered[0] = true);

        state.addCounter(1);

        assertTrue(triggered[0]);
    }

    @Test
    void shouldUpdatePurchasedBuildingsAndNotifyListeners() {
        boolean[] triggered = { false };
        state.addListener(() -> triggered[0] = true);

        state.incrementPurchasedBuildings(2);

        assertEquals(2, state.getPurchasedBuildings());
        assertTrue(triggered[0]);
    }

    @Test
    void shouldPerformClickUsingCounterPerClick() {
        state.doClick();

        assertEquals(10, state.getCounter());
        assertEquals(10, state.getTotalCounter());
    }

    @Test
    void shouldIncrementClicksPerSecond() {
        state.addClicksPerSecond(4);

        assertEquals(4, state.getClicksPerSecond());
    }
}
