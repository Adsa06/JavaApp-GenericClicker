package io.github.adsa06.data.local.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.adsa06.data.local.database.DatabaseConnection;
import io.github.adsa06.data.local.entity.SettingsEntity;
import io.github.adsa06.utilities.Utilities;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class SettingsDao {

    private final DatabaseConnection dbConfig;

    public SettingsDao(DatabaseConnection dbConfig) {
        this.dbConfig = dbConfig;
    }

    public SettingsEntity find() {
        String sql = "SELECT selectedLanguage FROM settings WHERE id = 1";

        SettingsEntity settingsEntity = new SettingsEntity("en");

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                settingsEntity = new SettingsEntity(
                    rs.getString("selectedLanguage")
                );
            }

        } catch (SQLException e) {
            Utilities.log("SettingsDao", e.getMessage());
        }

        return settingsEntity;
    }

    public void update(SettingsEntity entity) {

        String sql = "UPDATE settings SET selectedLanguage = ? WHERE id = 1";

        try (Connection conn = dbConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entity.language());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            Utilities.log("SettingsDao", e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "UPDATE settings SET selectedLanguage = 'en' WHERE id = 1";

        try (Connection conn = dbConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Utilities.log("SettingsDao", e.getMessage());
        }

    }
}
