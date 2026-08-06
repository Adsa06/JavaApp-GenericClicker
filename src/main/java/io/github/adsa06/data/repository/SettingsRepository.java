package io.github.adsa06.data.repository;

import java.util.Locale;

import io.github.adsa06.data.local.dao.SettingsDao;
import io.github.adsa06.data.local.mappers.SettingsMapper;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class SettingsRepository {
    
    private final SettingsDao settingsDao;
    private final SettingsMapper settingsMapper;
    
    public SettingsRepository(SettingsDao settingsDao, SettingsMapper settingsMapper) {
        this.settingsDao = settingsDao;
        this.settingsMapper = settingsMapper;
    }

    // SettingsDao
    public Locale findSettings() {
        return settingsMapper.toDomain(settingsDao.find());
    }

    public void updateSettings(Locale language) {
        settingsDao.update(settingsMapper.toEntity(language));
    }

    public void deleteSettings() {
        settingsDao.deleteAll();
    }
}
