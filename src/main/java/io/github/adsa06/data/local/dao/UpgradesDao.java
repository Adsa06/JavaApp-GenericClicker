package io.github.adsa06.data.local.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.utilities.Utilities;

public class UpgradesDao {
    private DatabaseConnection dbConfig;

    public UpgradesDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void saveAll(List<String> ids) {
        String sql = "INSERT INTO upgrades(id) VALUES(?)";

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (String id : ids) {
                pstmt.setString(1, id);
                pstmt.addBatch();
            }

            pstmt.executeBatch();


            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            Utilities.log("UpgradesDao", e.getMessage());
        }
    }

    public List<String> findAll() {
        List<String> upgrades = new ArrayList<>();
        String sql = "SELECT id FROM upgrades";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                upgrades.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            Utilities.log("UpgradesDao", e.getMessage());
        }
        return upgrades;
    }

    public void deleteAll() {
        String sql = "DELETE FROM upgrades";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Utilities.log("UpgradesDao", e.getMessage());
        }
    }   
}
