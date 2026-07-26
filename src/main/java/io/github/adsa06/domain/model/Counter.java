package io.github.adsa06.domain.model;

public class Counter {
    private int counter;

    public Counter() {
        counter = 0;
    }

    public Counter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
