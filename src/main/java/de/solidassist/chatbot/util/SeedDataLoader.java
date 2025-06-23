package de.solidassist.chatbot.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Loads initial seed data into the database if necessary.
 */
public class SeedDataLoader {

    /**
     * Inserts default chatbot settings if the settings table is empty.
     *
     * @param connection Active SQLite connection
     * @throws SQLException if database access fails
     */
    public static void insertDefaultSettingsIfEmpty(Connection connection) throws SQLException {
        // Check if the chatbot_settings table is empty
        String countQuery = "SELECT COUNT(*) FROM chatbot_settings";
        try (PreparedStatement countStmt = connection.prepareStatement(countQuery);
             ResultSet rs = countStmt.executeQuery()) {

            if (rs.next() && rs.getInt(1) == 0) {
                // Table is empty, insert default settings
                String insertQuery = """
                    INSERT INTO chatbot_settings (
                        profile_name, llm_server_url, llm_provider, llm_model_name,
                        model_access_token, reference_file_path,
                        max_tokens_percent, temperature_percent
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, "Default Profile");
                    insertStmt.setString(2, "http://localhost:11434");
                    insertStmt.setString(3, "ollama");
                    insertStmt.setString(4, "llama3");
                    insertStmt.setString(5, "");
                    insertStmt.setString(6, "");
                    insertStmt.setInt(7, 70);
                    insertStmt.setInt(8, 50);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}