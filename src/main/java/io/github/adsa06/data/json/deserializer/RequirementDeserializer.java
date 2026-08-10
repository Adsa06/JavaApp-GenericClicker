package io.github.adsa06.data.json.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.github.adsa06.data.json.dto.UpgradeDTO.BuildingReqPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.PointsReqPayload;
import io.github.adsa06.data.json.dto.UpgradeDTO.Requirement;
import io.github.adsa06.data.json.dto.UpgradeDTO.RequirementPayload;

public class RequirementDeserializer implements JsonDeserializer<Requirement> {

    @Override
    public Requirement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        JsonElement payloadElement = obj.get("payload");

        RequirementPayload payload = switch (type) {
            case "points" -> context.deserialize(payloadElement, PointsReqPayload.class);
            case "building" -> context.deserialize(payloadElement, BuildingReqPayload.class);
            default -> throw new JsonParseException("Tipo de requisito desconocido: " + type);
        };

        return new Requirement(type, payload);
    }
}