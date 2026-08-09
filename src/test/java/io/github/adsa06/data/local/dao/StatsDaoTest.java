package io.github.adsa06.data.local.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.data.local.entity.StatsEntity;
import io.github.adsa06.utilities.Config;

public class StatsDaoTest {

    private Connection anchorConnection;
    private StatsDao statsDao;


    @BeforeEach
    void setUp() throws SQLException {
        String dbUrl = "file:" + UUID.randomUUID() + "?mode=memory&cache=shared";

        anchorConnection = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);

        Config config = mock(Config.class);
        when(config.get("db.local.name")).thenReturn(dbUrl);

        DatabaseConnection dbConnection = new DatabaseConnection(config);
        statsDao = new StatsDao(dbConnection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (anchorConnection != null && !anchorConnection.isClosed()) {
            anchorConnection.close();
        }
    }

    @Test
    void shouldFindInitialStatsFromDatabase() {
        StatsEntity entity = statsDao.find();

        assertEquals(0, entity.counter());
        assertEquals(0, entity.totalCounter());
    }

    @Test
    void shouldUpdateAndReadStats() {
        StatsEntity afterUpdate = new StatsEntity(15, 20);
        statsDao.update(afterUpdate);

        StatsEntity entity = statsDao.find();

        assertEquals(15, entity.counter());
        assertEquals(20, entity.totalCounter());
    }

    @Test
    void shouldResetStatsToZero() {
        StatsEntity afterUpdate = new StatsEntity(7, 9);
        statsDao.update(afterUpdate);
        statsDao.deleteAll();

        StatsEntity entity = statsDao.find();

        assertEquals(0, entity.counter());
        assertEquals(0, entity.totalCounter());
    }
}
