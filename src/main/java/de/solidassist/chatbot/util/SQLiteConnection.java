package de.solidassist.chatbot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Path;

/**
 * SQLiteConnection provides connections to the SQLite database.
 * It ensures foreign key constraints are enabled for each connection.
 */
public class SQLiteConnection {

    /**
     * Returns a connection to the SQLite database with foreign key constraints enabled.
     *
     * @return SQLite connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        // Get the full path to the database file
        Path dbPath = DatabasePathUtils.getDatabaseFilePath();
        String dbUrl = "jdbc:sqlite:" + dbPath;

        try {
            // Ensure the SQLite JDBC driver is registered
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }

        Connection conn = DriverManager.getConnection(dbUrl);

        // Enable foreign key constraints
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }
}