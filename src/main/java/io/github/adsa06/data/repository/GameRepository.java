package io.github.adsa06.data.repository;

import java.util.List;

import io.github.adsa06.data.local.dao.AchievementsDao;
import io.github.adsa06.data.local.dao.BuildingsDao;
import io.github.adsa06.data.local.dao.StatsDao;
import io.github.adsa06.data.local.dao.UpgradesDao;
import io.github.adsa06.data.local.mappers.BuildingMapper;
import io.github.adsa06.data.local.mappers.StatsMapper;
import io.github.adsa06.domain.model.Building;
import io.github.adsa06.domain.model.GameState;

public class GameRepository {

    private AchievementsDao achievementsDao;
    private StatsDao statsDao;
    private StatsMapper statsMapper = new StatsMapper();
    private BuildingsDao buildingsDao;
    private BuildingMapper buildingMapper = new BuildingMapper();

    private UpgradesDao upgradesDao;

    public GameRepository(AchievementsDao achievementsDao, StatsDao statsDao, BuildingsDao buildingsDao, UpgradesDao upgradesDao) {
        this.achievementsDao = achievementsDao;
        this.statsDao = statsDao;
        this.buildingsDao = buildingsDao;
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

    public void saveBuildings(List<Building> buildings) {
        buildingsDao.saveAll(buildings.stream().map(buildingMapper::toEntity).toList());
    }

    public void deleteAllBuildings() {
        buildingsDao.deleteAll();
    }

    // UpgradesDao
    
}
