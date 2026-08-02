package io.github.adsa06.data.repository;

import java.util.Locale;

import io.github.adsa06.data.local.dao.SettingsDao;
import io.github.adsa06.data.local.mappers.SettingsMapper;

public class SettingsRepository {
    
    private SettingsDao settingsDao;
    private SettingsMapper settingsMapper = new SettingsMapper();
    
    public SettingsRepository(SettingsDao settingsDao) {
        this.settingsDao = settingsDao;
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
