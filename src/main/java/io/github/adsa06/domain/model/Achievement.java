package io.github.adsa06.domain.model;

import java.util.function.Predicate;

public class Achievement {
    private String id;
    private String titleId;
    private String descripcionId;
    private boolean finished = false;
    private Predicate<GameState> condition;

    public Achievement(String id, String titleId, String descripcionId, Predicate<GameState> condition) {
        this.id = id;
        this.titleId = titleId;
        this.descripcionId = descripcionId;
        this.condition = condition;
    }

    public boolean checkAndUpdate(GameState state) {
        boolean unlocked = !finished && condition.test(state);
        if (unlocked) {
            finished = true;
        }
        return unlocked;
    }

    public boolean isFinished() {
        return finished;
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

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
