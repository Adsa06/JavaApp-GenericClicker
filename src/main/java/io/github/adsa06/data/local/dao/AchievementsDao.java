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

public class AchievementsDao {
    private DatabaseConnection dbConfig;

    public AchievementsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void saveAll(List<String> ids) {
        String sql = "INSERT INTO achievements(id) VALUES(?)";

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
            Utilities.log("AchievementsDao", e.getMessage());
        }
    }

    public List<String> findAll() {
        List<String> achievements = new ArrayList<>();
        String sql = "SELECT id FROM achievements";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                achievements.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            Utilities.log("AchievementsDao", e.getMessage());
        }
        return achievements;
    }

    public void deleteAll() {
        String sql = "DELETE FROM achievements";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Utilities.log("AchievementsDao", e.getMessage());
        }
    }

}
