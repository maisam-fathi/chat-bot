package de.solidassist.chatbot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLiteConnection provides connections to the SQLite database.
 * It ensures foreign key constraints are enabled for each connection.
 */
public class SQLiteConnection {

    private static final String DB_URL = "jdbc:sqlite:solidassist_chatcore.db";

    /**
     * Returns a connection to the SQLite database with foreign key constraints enabled.
     *
     * @return SQLite connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);

        // Enable foreign key constraints
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }
}