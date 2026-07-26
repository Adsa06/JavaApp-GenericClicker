package io.github.adsa06.presentation.viewmodel;

import io.github.adsa06.domain.model.Counter;

public class GameViewModel {
    private Counter counter;

    public GameViewModel() {
        counter = new Counter();
    }

    public int getNumber() {
        return counter.getCounter();
    }

    public void setNumber(int num) {
        counter.setCounter(num);
    }

    public void increaseNumberBy(int num) {
        counter.setCounter(counter.getCounter() + num);
    }
}
