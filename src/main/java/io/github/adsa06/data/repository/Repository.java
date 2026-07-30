package io.github.adsa06.data.repository;

import java.util.List;

import io.github.adsa06.data.local.dao.AchievementsDao;
import io.github.adsa06.data.local.dao.StatsDao;
import io.github.adsa06.data.local.dao.UpgradesDao;
import io.github.adsa06.data.local.mappers.StatsMapper;
import io.github.adsa06.domain.model.GameState;

public class Repository {

    private AchievementsDao achievementsDao;
    private StatsDao statsDao;
    private StatsMapper statsMapper = new StatsMapper();
    private UpgradesDao upgradesDao;

    public Repository(AchievementsDao achievementsDao, StatsDao statsDao, UpgradesDao upgradesDao) {
        this.achievementsDao = achievementsDao;
        this.statsDao = statsDao;
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

    // UpgradesDao
    
}
