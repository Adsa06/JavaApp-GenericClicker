package io.github.adsa06.data.json.dto;

// --- MODELO GENERAL ---
public record UpgradeDTO(
        String id,
        long cost,
        Requirement required,
        Effect effect) {
    public record Requirement(
            String type,
            RequirementPayload payload) {
    }

    public record Effect(
            String type,
            EffectPayload payload) {
    }

    // --- POLIMORFISMO DE REQUISITOS ---
    public sealed interface RequirementPayload permits PointsReqPayload, BuildingReqPayload {
    }

    public record PointsReqPayload(int amount) implements RequirementPayload {
    }

    public record BuildingReqPayload(String id, int amount) implements RequirementPayload {
    }

    // --- POLIMORFISMO DE EFECTOS ---
    public sealed interface EffectPayload permits ClickEffectPayload, BuildingEffectPayload {
    }

    public record ClickEffectPayload(int amount) implements EffectPayload {
    }

    public record BuildingEffectPayload(String id, int multiplier) implements EffectPayload {
    }
}
