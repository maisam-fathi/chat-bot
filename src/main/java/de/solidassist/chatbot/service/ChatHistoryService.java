package de.solidassist.chatbot.service;

/* Import DAO and model classes */
import de.solidassist.chatbot.dao.ChatSessionDAO;
import de.solidassist.chatbot.dao.ChatMessageDAO;
import de.solidassist.chatbot.model.ChatSession;
import de.solidassist.chatbot.model.ChatMessage;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class responsible for managing chat history for GUI.
 * <p>
 * Handles session and message retrieval and management,
 * delegating database operations to DAO classes.
 * This class is designed to be independent of Spring Boot
 * and used directly by the Swing GUI layer.
 */
public class ChatHistoryService {

    private final ChatSessionDAO sessionDAO = new ChatSessionDAO();
    private final ChatMessageDAO messageDAO = new ChatMessageDAO();

    /**
     * Retrieves all chat sessions from the database.
     *
     * @return list of all chat sessions
     * @throws SQLException if database access fails
     */
    public List<ChatSession> getAllSessions() throws SQLException {
        return sessionDAO.getAll();
    }

    /**
     * Retrieves all chat messages for a given session ID.
     *
     * @param sessionId the session ID to retrieve messages for
     * @return list of messages in the session
     * @throws SQLException if database access fails
     */
    public List<ChatMessage> getMessagesForSession(int sessionId) throws SQLException {
        return messageDAO.getMessagesBySessionId(sessionId);
    }

    /**
     * Inserts a new chat session.
     *
     * @param sessionName name of the session
     * @return generated session ID
     * @throws SQLException if database access fails
     */
    public int createSession(String sessionName) throws SQLException {
        ChatSession session = new ChatSession();
        session.setSessionName(sessionName);
        return sessionDAO.insert(session);
    }

    /**
     * Inserts a new chat message to a session.
     *
     * @param sessionId session ID to attach the message to
     * @param sender    sender of the message ("user" or "bot")
     * @param message   message content
     * @return generated message ID
     * @throws SQLException if database access fails
     */
    public int addMessageToSession(int sessionId, String sender, String message) throws SQLException {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setSender(sender);
        chatMessage.setMessage(message);
        return messageDAO.insert(chatMessage);
    }
}