package io.github.adsa06.data.local.mappers;

import java.util.Locale;

import io.github.adsa06.data.local.entity.SettingsEntity;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class SettingsMapper implements LocalMapper<Locale, SettingsEntity> {

    @Override
    public Locale toDomain(SettingsEntity entity) {
        return Locale.of(entity.language());
    }

    @Override
    public SettingsEntity toEntity(Locale domain) {
        return new SettingsEntity(domain.getLanguage());
    }

}
