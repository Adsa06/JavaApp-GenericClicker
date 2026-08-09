package io.github.adsa06.data.local.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.utilities.Config;

public class AchievementsDaoTest {

    private Connection anchorConnection;
    private AchievementsDao achievementsDao;


    @BeforeEach
    void setUp() throws SQLException {
        String dbUrl = "file:" + UUID.randomUUID() + "?mode=memory&cache=shared";

        anchorConnection = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);

        Config config = mock(Config.class);
        when(config.get("db.local.name")).thenReturn(dbUrl);

        DatabaseConnection dbConnection = new DatabaseConnection(config);
        achievementsDao = new AchievementsDao(dbConnection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (anchorConnection != null && !anchorConnection.isClosed()) {
            anchorConnection.close();
        }
    }

    @Test
    void shouldFindNothingFromDatabase() {
        List<String> entitys = achievementsDao.findAll();

        assertEquals(0, entitys.size());
    }
}