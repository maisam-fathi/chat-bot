package de.solidassist.chatbot.dao;

import de.solidassist.chatbot.model.ChatbotSettings;
import de.solidassist.chatbot.util.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatbotSettingsDAO provides full CRUD operations for the chatbot_settings table.
 * Allows managing multiple configurations for different models or use cases.
 */
public class ChatbotSettingsDAO {

    /**
     * Inserts a new ChatbotSettings record.
     *
     * @param settings ChatbotSettings object.
     * @return Generated ID.
     * @throws SQLException if a database access error occurs.
     */
    public int insertSettings(ChatbotSettings settings) throws SQLException {
        String sql = "INSERT INTO chatbot_settings " +
                "(profile_name, llm_server_url, llm_provider, llm_model_name, model_access_token, " +
                "reference_file_path, max_tokens_percent, temperature_percent) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, settings.getProfileName());
            stmt.setString(2, settings.getLlmServerUrl());
            stmt.setString(3, settings.getLlmProvider());
            stmt.setString(4, settings.getLlmModelName());
            stmt.setString(5, settings.getModelAccessToken());
            stmt.setString(6, settings.getReferenceFilePath());
            stmt.setInt(7, settings.getMaxTokensPercent());
            stmt.setInt(8, settings.getTemperaturePercent());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting settings failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting settings failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Retrieves a ChatbotSettings by its ID.
     *
     * @param id The settings ID.
     * @return ChatbotSettings object or null if not found.
     * @throws SQLException if a database access error occurs.
     */
    public ChatbotSettings getSettingsById(int id) throws SQLException {
        String sql = "SELECT * FROM chatbot_settings WHERE id = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSettings(rs);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all ChatbotSettings records.
     *
     * @return List of ChatbotSettings objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<ChatbotSettings> getAllSettings() throws SQLException {
        List<ChatbotSettings> settingsList = new ArrayList<>();
        String sql = "SELECT * FROM chatbot_settings ORDER BY updated_at DESC";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                settingsList.add(mapResultSetToSettings(rs));
            }
        }
        return settingsList;
    }

    /**
     * Updates the existing ChatbotSettings by ID.
     *
     * @param settings ChatbotSettings object with updated data.
     * @return true if updated successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean updateSettings(ChatbotSettings settings) throws SQLException {
        String sql = "UPDATE chatbot_settings SET " +
                "profile_name = ?, " +
                "llm_server_url = ?, " +
                "llm_provider = ?, " +
                "llm_model_name = ?, " +
                "model_access_token = ?, " +
                "reference_file_path = ?, " +
                "max_tokens_percent = ?, " +
                "temperature_percent = ?, " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, settings.getProfileName());
            stmt.setString(2, settings.getLlmServerUrl());
            stmt.setString(3, settings.getLlmProvider());
            stmt.setString(4, settings.getLlmModelName());
            stmt.setString(5, settings.getModelAccessToken());
            stmt.setString(6, settings.getReferenceFilePath());
            stmt.setInt(7, settings.getMaxTokensPercent());
            stmt.setInt(8, settings.getTemperaturePercent());
            stmt.setInt(9, settings.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a ChatbotSettings record by its ID.
     *
     * @param id The settings ID.
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteSettings(int id) throws SQLException {
        String sql = "DELETE FROM chatbot_settings WHERE id = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Maps a ResultSet row to a ChatbotSettings object.
     *
     * @param rs ResultSet.
     * @return ChatbotSettings object.
     * @throws SQLException if a database access error occurs.
     */
    private ChatbotSettings mapResultSetToSettings(ResultSet rs) throws SQLException {
        ChatbotSettings settings = new ChatbotSettings();
        settings.setId(rs.getInt("id"));
        settings.setProfileName(rs.getString("profile_name")); // ← new
        settings.setLlmServerUrl(rs.getString("llm_server_url"));
        settings.setLlmProvider(rs.getString("llm_provider"));
        settings.setLlmModelName(rs.getString("llm_model_name"));
        settings.setModelAccessToken(rs.getString("model_access_token")); // ← new
        settings.setReferenceFilePath(rs.getString("reference_file_path")); // ← new
        settings.setMaxTokensPercent(rs.getInt("max_tokens_percent"));
        settings.setTemperaturePercent(rs.getInt("temperature_percent"));
        settings.setUpdatedAt(rs.getTimestamp("updated_at"));
        return settings;
    }
}