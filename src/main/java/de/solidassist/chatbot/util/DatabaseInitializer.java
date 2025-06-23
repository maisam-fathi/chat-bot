package de.solidassist.chatbot.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes the SQLite database by creating required tables if they do not exist.
 */
public class DatabaseInitializer {

    /**
     * Creates the necessary tables if they do not exist.
     *
     * @param connection Active SQLite connection
     * @throws SQLException if table creation fails
     */
    public static void initializeDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            // Create the chat_sessions table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS chat_sessions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    session_name TEXT NOT NULL,
                    created_at DATETIME DEFAULT (datetime('now'))
                );
            """);

            // Create the chat_messages table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS chat_messages (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    session_id INTEGER NOT NULL,
                    sender TEXT CHECK(sender IN ('user', 'bot')) NOT NULL,
                    message TEXT NOT NULL,
                    created_at DATETIME DEFAULT (datetime('now')),
                    FOREIGN KEY (session_id) REFERENCES chat_sessions(id) ON DELETE CASCADE
                );
            """);

            // Create the chatbot_settings table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS chatbot_settings (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    profile_name TEXT NOT NULL,
                    llm_server_url TEXT NOT NULL,
                    llm_provider TEXT NOT NULL,
                    llm_model_name TEXT NOT NULL,
                    model_access_token TEXT DEFAULT '',
                    reference_file_path TEXT DEFAULT '',
                    max_tokens_percent INTEGER NOT NULL,
                    temperature_percent INTEGER NOT NULL,
                    updated_at DATETIME DEFAULT (datetime('now'))
                );
            """);
        }
    }
}