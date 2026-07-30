package io.github.adsa06.data.local.mappers;

import io.github.adsa06.data.local.entity.StatsEntity;
import io.github.adsa06.domain.model.GameState;

public class StatsMapper implements Mapper<GameState, StatsEntity> {

    @Override
    public GameState toDomain(StatsEntity entity) {
        return new GameState(entity.counter(), entity.clicksPerSecond(), entity.unlockedUpgrades());
    }

    @Override
    public StatsEntity toEntity(GameState domain) {
        return new StatsEntity(domain.getCounter(), domain.getClicksPerSecond(), domain.getUnlockedUpgrades());
    }
    
}
