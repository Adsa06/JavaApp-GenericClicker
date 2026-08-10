package io.github.adsa06.data.repository;

import java.util.List;
import java.util.Set;

import io.github.adsa06.data.local.dao.AchievementsDao;
import io.github.adsa06.data.local.dao.BuildingsDao;
import io.github.adsa06.data.local.dao.StatsDao;
import io.github.adsa06.data.local.dao.UpgradesDao;
import io.github.adsa06.data.local.mappers.LocalBuildingMapper;
import io.github.adsa06.data.local.mappers.StatsMapper;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class GameRepository {

    private final AchievementsDao achievementsDao;
    private final StatsDao statsDao;
    private final StatsMapper statsMapper;
    private final BuildingsDao buildingsDao;
    private final LocalBuildingMapper buildingMapper;
    private final UpgradesDao upgradesDao;

    public GameRepository(AchievementsDao achievementsDao, StatsDao statsDao, StatsMapper statsMapper,
            BuildingsDao buildingsDao, LocalBuildingMapper buildingMapper, UpgradesDao upgradesDao) {
        this.achievementsDao = achievementsDao;
        this.statsDao = statsDao;
        this.statsMapper = statsMapper;
        this.buildingsDao = buildingsDao;
        this.buildingMapper = buildingMapper;
        this.upgradesDao = upgradesDao;
    }

    // AchievementsDao
    public List<String> findAllAchievements() {
        return achievementsDao.findAll();
    }

    public void saveAchievements(List<String> ids) {
        achievementsDao.saveAll(ids);
    }

    public void deleteAllAchievements() {
        achievementsDao.deleteAll();
    }

    // StatsDao
    public GameState findStats() {
        return statsMapper.toDomain(statsDao.find());
    }

    public void updateStats(GameState gameState) {
        statsDao.update(statsMapper.toEntity(gameState));
    }

    public void deleteStats() {
        statsDao.deleteAll();
    }

    // Buildings
    public List<Building> findAllBuildings() {
        return buildingsDao.findAll().stream().map(buildingMapper::toDomain).toList();
    }

    public void saveBuildings(Set<Building> buildings) {
        buildingsDao.saveAll(buildings.stream().map(buildingMapper::toEntity).toList());
    }

    public void deleteAllBuildings() {
        buildingsDao.deleteAll();
    }

    // UpgradesDao
    public List<String> findAllUpgrades() {
        return upgradesDao.findAll();
    }

    public void saveUpgrades(List<String> ids) {
        upgradesDao.saveAll(ids);
    }

    public void deleteAllUpgrades() {
        upgradesDao.deleteAll();
    }
}
