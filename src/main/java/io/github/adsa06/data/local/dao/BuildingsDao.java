package io.github.adsa06.data.local.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.data.local.entity.BuildingEntity;
import io.github.adsa06.utilities.Utilities;

public class BuildingsDao {
    private DatabaseConnection dbConfig;

    public BuildingsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void saveAll(List<BuildingEntity> buildings) {
        String sql = "INSERT INTO buildings (id, level, cost) VALUES (?, ?, ?) " +
                            "ON CONFLICT(id) DO UPDATE SET " +
                            "level = excluded.level, " +
                            "cost = excluded.cost";

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (BuildingEntity building : buildings) {
                pstmt.setString(1, building.id());
                pstmt.setInt(2, building.level());
                pstmt.setLong(3, building.cost());
                pstmt.addBatch();
            }

            pstmt.executeBatch();


            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            Utilities.log("BuildingsDao", e.getMessage());
        }
    }

    public List<BuildingEntity> findAll() {
        List<BuildingEntity> buildings = new ArrayList<>();
        String sql = "SELECT id, level, cost FROM buildings";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                buildings.add(new BuildingEntity(
                    rs.getString("id"),
                    rs.getInt("level"),
                    rs.getLong("cost")));
            }
        } catch (SQLException e) {
            Utilities.log("BuildingsDao", e.getMessage());
        }
        return buildings;
    }

    public void deleteAll() {
        String sql = "DELETE FROM buildings";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Utilities.log("BuildingsDao", e.getMessage());
        }
    }  
}
