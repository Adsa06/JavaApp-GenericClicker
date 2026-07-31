package io.github.adsa06.data.local.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.data.local.entity.StatsEntity;
import io.github.adsa06.utilities.Utilities;

public class StatsDao {
    private DatabaseConnection dbConfig;

    public StatsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public StatsEntity find() {
        String sql = "SELECT actualCounter, clicksPerSecond, counterPerClick, purchasedBuildings FROM stats WHERE id = 1";

        StatsEntity entity = new StatsEntity(0L, 0L, 10L, 0L);

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                entity = new StatsEntity(
                        rs.getLong("actualCounter"),
                        rs.getLong("clicksPerSecond"),
                        rs.getLong("counterPerClick"),
                        rs.getLong("purchasedBuildings"));
            }

        } catch (SQLException e) {
            Utilities.log("StatsDao", e.getMessage());
        }

        return entity;
    }

    public void update(StatsEntity entity) {

        String sql = "UPDATE stats SET actualCounter = ?, clicksPerSecond = ?, counterPerClick = ?, purchasedBuildings = ? WHERE id = 1";

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, entity.counter());
            pstmt.setLong(2, entity.clicksPerSecond());
            pstmt.setLong(3, entity.counterPerClick());
            pstmt.setLong(4, entity.purchasedBuildings());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            Utilities.log("StatsDao", e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "UPDATE stats SET actualCounter = 0, clicksPerSecond = 0, counterPerClick = 10, purchasedBuildings = 0 WHERE id = 1";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Utilities.log("StatsDao", e.getMessage());
        }

    }
}
