package io.github.adsa06.data.local.mappers;

import io.github.adsa06.data.local.entity.BuildingEntity;
import io.github.adsa06.domain.model.Building;

public class BuildingMapper implements Mapper<Building, BuildingEntity> {

    @Override
    public Building toDomain(BuildingEntity entity) {
        return new Building(entity.id(), entity.level(), entity.cost());
    }

    @Override
    public BuildingEntity toEntity(Building domain) {
        return new BuildingEntity(domain.getId(), domain.getLevel(), domain.getCost());
    }
    
}
