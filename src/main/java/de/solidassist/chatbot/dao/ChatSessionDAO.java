package de.solidassist.chatbot.dao;

import de.solidassist.chatbot.model.ChatSession;
import de.solidassist.chatbot.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatSessionDAO provides CRUD operations for the chat_sessions table.
 * It handles database interactions related to chat sessions.
 */
public class ChatSessionDAO {

    /**
     * Inserts a new ChatSession into the database.
     *
     * @param session ChatSession object containing session_name.
     * @return Generated session ID.
     * @throws SQLException if a database access error occurs.
     */
    public int insert(ChatSession session) throws SQLException {
        String sql = "INSERT INTO chat_sessions (session_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, session.getSessionName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating session failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating session failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Retrieves a ChatSession by its ID.
     *
     * @param id The session ID.
     * @return ChatSession object or null if not found.
     * @throws SQLException if a database access error occurs.
     */
    public ChatSession getById(int id) throws SQLException {
        String sql = "SELECT * FROM chat_sessions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ChatSession session = new ChatSession();
                    session.setId(rs.getInt("id"));
                    session.setSessionName(rs.getString("session_name"));
                    session.setCreatedAt(rs.getTimestamp("created_at"));
                    return session;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all ChatSessions from the database.
     *
     * @return List of ChatSession objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<ChatSession> getAll() throws SQLException {
        List<ChatSession> sessions = new ArrayList<>();
        String sql = "SELECT * FROM chat_sessions ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ChatSession session = new ChatSession();
                session.setId(rs.getInt("id"));
                session.setSessionName(rs.getString("session_name"));
                session.setCreatedAt(rs.getTimestamp("created_at"));
                sessions.add(session);
            }
        }
        return sessions;
    }

    /**
     * Updates a ChatSession's session_name by its ID.
     *
     * @param session ChatSession object with updated data.
     * @return true if updated successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean update(ChatSession session) throws SQLException {
        String sql = "UPDATE chat_sessions SET session_name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, session.getSessionName());
            stmt.setInt(2, session.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a ChatSession by its ID using the provided connection.
     *
     * @param conn The active database connection.
     * @param id   The session ID.
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM chat_sessions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }
}