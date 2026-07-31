package io.github.adsa06.presentation.viewmodel;

import io.github.adsa06.domain.model.GameState;

public class GameViewModel {

    private final GameState state;

    public GameViewModel(GameState state) {
        this.state = state;
    }

    public void addListener(Runnable callback) {
        state.addListener(callback);
    }

    public long getCounter() {
        return state.getCounter();
    }

    public void onClickButtonPressed() {
        state.doClick();
    }
}
