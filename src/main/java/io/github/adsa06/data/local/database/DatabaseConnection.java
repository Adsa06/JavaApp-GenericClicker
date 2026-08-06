package io.github.adsa06.data.local.database;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.adsa06.utilities.Config;
import io.github.adsa06.utilities.Utilities;
import io.github.adsa06.utilities.di.Singleton;

@Singleton
public class DatabaseConnection {

    private String dbUrl;

    // Constructor dinámico: permite cambiar la base de datos fácilmente
    public DatabaseConnection(Config config) {
        this.dbUrl = "jdbc:sqlite:" + config.get("db.local.name");
        initDatabase();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    public void initDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                InputStream is = getClass().getClassLoader().getResourceAsStream("db/schema.sql")) {

            if (is != null) {
                String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            Utilities.log("DatabaseConnection", e.getMessage());
        }
    }
}
