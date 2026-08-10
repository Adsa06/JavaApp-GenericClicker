package io.github.adsa06.data.json.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.ClickEffectPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.Effect;
import io.github.adsa06.data.json.dto.UpgradeDTO.EffectPayload;

public class EffectDeserializer implements JsonDeserializer<Effect> {

    @Override
    public Effect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        JsonElement payloadElement = obj.get("payload");

        EffectPayload payload = switch (type) {
            case "click" -> context.deserialize(payloadElement, ClickEffectPayload.class);
            case "building" -> context.deserialize(payloadElement, BuildingEffectPayload.class);
            default -> throw new JsonParseException("Tipo de efecto desconocido: " + type);
        };

        return new Effect(type, payload);
    }

}
