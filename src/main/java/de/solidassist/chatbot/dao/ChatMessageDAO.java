package de.solidassist.chatbot.dao;

import de.solidassist.chatbot.model.ChatMessage;
import de.solidassist.chatbot.util.DatabaseConnection;

import java.util.Collections;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatMessageDAO provides CRUD operations for the chat_messages table.
 * It handles database interactions related to chat messages.
 */
public class ChatMessageDAO {

    /**
     * Inserts a new ChatMessage into the database.
     *
     * @param message ChatMessage object containing message data.
     * @return Generated message ID.
     * @throws SQLException if a database access error occurs.
     */
    public int insert(ChatMessage message) throws SQLException {
        String sql = "INSERT INTO chat_messages (session_id, sender, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, message.getSessionId());
            stmt.setString(2, message.getSender());
            stmt.setString(3, message.getMessage());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Retrieves all messages for a specific session ID.
     *
     * @param sessionId The session ID.
     * @return List of ChatMessage objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<ChatMessage> getMessagesBySessionId(int sessionId) throws SQLException {
        List<ChatMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM chat_messages WHERE session_id = ? ORDER BY created_at ASC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChatMessage message = new ChatMessage();
                    message.setId(rs.getInt("id"));
                    message.setSessionId(rs.getInt("session_id"));
                    message.setSender(rs.getString("sender"));
                    message.setMessage(rs.getString("message"));
                    message.setCreatedAt(rs.getTimestamp("created_at"));
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    /**
     * Deletes all messages by session ID using the provided connection.
     *
     * @param conn      The active database connection.
     * @param sessionId The session ID.
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteMessagesBySessionId(Connection conn, int sessionId) throws SQLException {
        String sql = "DELETE FROM chat_messages WHERE session_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sessionId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a single message by its ID.
     *
     * @param id The message ID.
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM chat_messages WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

    private static final Logger logger = Logger.getLogger(ChatMessageDAO.class.getName());

    /**
     * Retrieves the last N messages for a given session, ordered by timestamp ascending.
     *
     * @param sessionId the ID of the chat session
     * @param limit     the maximum number of messages to retrieve
     * @return a list of ChatMessage objects ordered by time (oldest to newest)
     */
    public List<ChatMessage> getLastNMessagesBySessionId(int sessionId, int limit) {
        List<ChatMessage> messages = new ArrayList<>();

        String sql = "SELECT * FROM chat_messages WHERE session_id = ? ORDER BY created_at DESC LIMIT ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            stmt.setInt(2, limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChatMessage message = new ChatMessage();
                message.setId(rs.getInt("id"));
                message.setSessionId(rs.getInt("session_id"));
                message.setSender(rs.getString("sender"));
                message.setMessage(rs.getString("message"));

                // Convert SQL timestamp to java.util.Date
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    message.setCreatedAt(new Date(timestamp.getTime()));
                }

                messages.add(message);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error loading last N messages", e);
        }

        // Reverse list to ensure oldest-to-newest order
        Collections.reverse(messages);
        return messages;
    }
}