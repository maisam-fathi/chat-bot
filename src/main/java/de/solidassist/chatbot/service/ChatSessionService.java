package de.solidassist.chatbot.service;

import de.solidassist.chatbot.dao.ChatSessionDAO;
import de.solidassist.chatbot.dao.ChatMessageDAO;
import de.solidassist.chatbot.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class to handle chat session related business logic,
 * including transactional deletion of a session and its messages.
 */
public class ChatSessionService {

    private static final Logger logger = Logger.getLogger(ChatSessionService.class.getName());

    private final ChatSessionDAO sessionDAO = new ChatSessionDAO();
    private final ChatMessageDAO messageDAO = new ChatMessageDAO();

    /**
     * Deletes a chat session along with all its messages in a single transaction.
     * <p>
     * This method relies on the ON DELETE CASCADE constraint in SQLite to automatically
     * remove all messages linked to the session.
     *
     * @param sessionId The ID of the session to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteSessionWithMessages(int sessionId) {
        try (Connection conn = SQLiteConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Only delete the session — messages will be deleted automatically via ON DELETE CASCADE
            boolean sessionDeleted = sessionDAO.delete(conn, sessionId);

            if (sessionDeleted) {
                conn.commit();
                logger.info("Deleted session and its messages successfully, sessionId: " + sessionId);
                return true;
            } else {
                conn.rollback();
                logger.warning("Failed to delete session, rolled back transaction, sessionId: " + sessionId);
                return false;
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQLException during deleteSessionWithMessages, rolling back", ex);
            return false;
        }
    }
}