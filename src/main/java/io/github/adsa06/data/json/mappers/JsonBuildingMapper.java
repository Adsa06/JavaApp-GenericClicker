package io.github.adsa06.data.json.mappers;

import java.util.LinkedHashMap;
import java.util.Map;

import io.github.adsa06.data.json.dto.BuildingDTO;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonBuildingMapper {

    public Map<String, Building> toDomain(LinkedHashMap<String, BuildingDTO> buildingDTO) {
        Map<String, Building> buildings = new LinkedHashMap<>();

        buildingDTO.forEach((id, object) -> {
            Building builing = new Building(id, id + "Title", id + "Description", object.baseCost(),
                    object.baseProduction());
            buildings.put(id, builing);
        });
        return buildings;
    }

}
