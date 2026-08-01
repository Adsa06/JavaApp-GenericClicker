package io.github.adsa06.domain.model;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Upgrade {
    private String id;
    private String titleId;
    private String descripcionId;
    private boolean purchased = false;
    private Predicate<GameState> condition;
    private long cost;

    //private UpgradeEffect effect;
    private Consumer effect1;

}
