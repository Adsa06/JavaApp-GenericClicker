package io.github.adsa06.data.json.mappers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import io.github.adsa06.data.json.dto.UpgradeDTO;
import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingReqPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.ClickEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.PointsReqPayload;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.domain.model.UpgradeEffects.BuildingMultiplierEffect;
import io.github.adsa06.domain.model.UpgradeEffects.ClickBonusEffect;
import io.github.adsa06.domain.model.UpgradeEffects.UpgradeEffect;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class JsonUpgradeMapper {

    public Map<String, Upgrade> toDomain(LinkedHashMap<String, UpgradeDTO> upgradeDTO) {
        Map<String, Upgrade> upgrades = new LinkedHashMap<>();

        upgradeDTO.forEach((id, object) -> {

            UpgradeEffect effect = switch (object.effect().payload()) {
                case ClickEffectPayload eff -> new ClickBonusEffect(eff.amount());
                case BuildingEffectPayload eff -> new BuildingMultiplierEffect(eff.id(), eff.multiplier());
            };

            Predicate<GameState> condition = switch (object.required().payload()) {
                case PointsReqPayload req -> (gameState) -> gameState.getTotalCounter() >= req.amount();
                case BuildingReqPayload req -> (gameState) -> gameState.getPurchasedBuildings(req.id()) >= req.amount();
            };
            
            Upgrade upgrade = new Upgrade(id, id + "Title", id + "Description", object.cost(), effect, condition);
            upgrades.put(id, upgrade);
        });

        return upgrades;
    }

}
