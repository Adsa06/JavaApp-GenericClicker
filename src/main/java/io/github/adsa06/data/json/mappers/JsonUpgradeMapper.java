package io.github.adsa06.data.json.mappers;

import java.util.LinkedHashMap;
import java.util.Map;

import io.github.adsa06.data.json.dto.UpgradeDTO;
import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingReqPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.ClickEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.PointsReqPayload;
import io.github.adsa06.domain.model.Upgrade;
import io.github.adsa06.domain.model.UpgradeEffects.BuildingMultiplierEffect;
import io.github.adsa06.domain.model.UpgradeEffects.ClickBonusEffect;
import io.github.adsa06.domain.model.UpgradeEffects.UpgradeEffect;
import io.github.adsa06.domain.model.UpgradeRequirements.BuildingAmountRequirement;
import io.github.adsa06.domain.model.UpgradeRequirements.ClickAmountRequirement;
import io.github.adsa06.domain.model.UpgradeRequirements.UpgradeRequirement;
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

            UpgradeRequirement condition = switch (object.required().payload()) {
                case PointsReqPayload req -> new ClickAmountRequirement(req.amount());
                case BuildingReqPayload req -> new BuildingAmountRequirement(req.id(), req.amount());
            };
            
            Upgrade upgrade = new Upgrade(id, id + "Title", id + "Description", object.cost(), effect, condition);
            upgrades.put(id, upgrade);
        });

        return upgrades;
    }

}
