package de.solidassist.chatbot.service;

/* Import DAO and model classes */
import de.solidassist.chatbot.dao.ChatSessionDAO;
import de.solidassist.chatbot.dao.ChatMessageDAO;
import de.solidassist.chatbot.model.ChatSession;
import de.solidassist.chatbot.model.ChatMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Deletes a session and all its associated messages.
     *
     * @param sessionId The session ID to delete.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean deleteSessionWithMessages(int sessionId) {
        try {
            ChatSessionService sessionService = new ChatSessionService();
            return sessionService.deleteSessionWithMessages(sessionId);
        } catch (Exception e) {
            Logger.getLogger(ChatHistoryService.class.getName()).log(Level.SEVERE, "Failed to delete session with messages", e);
            return false;
        }
    }

    /**
     * Retrieves the last N messages of a session ordered by creation time.
     *
     * @param sessionId the session ID to fetch messages for
     * @param limit     the maximum number of messages to return
     * @return list of the most recent messages for the session
     */
    public List<ChatMessage> getLastNMessagesForSession(int sessionId, int limit) {
        return messageDAO.getLastNMessagesBySessionId(sessionId, limit);
    }

    /**
     * Retrieves the last N chat messages for a session and trims bot messages to a word limit.
     *
     * @param sessionId    the ID of the session to retrieve messages from
     * @param limit        the number of recent messages to retrieve
     * @param botWordLimit the maximum number of words allowed in bot messages
     * @return a list of chat messages, with bot messages trimmed if necessary
     */
    public List<ChatMessage> getTrimmedMessagesForMemory(int sessionId, int limit, int botWordLimit) {
        List<ChatMessage> rawMessages = new ArrayList<>();
        List<ChatMessage> trimmedMessages = new ArrayList<>();

        try {
            // Load the most recent N messages from the database
            rawMessages = messageDAO.getLastNMessagesBySessionId(sessionId, limit);

            for (ChatMessage message : rawMessages) {
                // Clone the message to avoid modifying the original list
                ChatMessage trimmedMessage = new ChatMessage();
                trimmedMessage.setId(message.getId());
                trimmedMessage.setSessionId(message.getSessionId());
                trimmedMessage.setSender(message.getSender());
                trimmedMessage.setCreatedAt(message.getCreatedAt());

                if ("bot".equalsIgnoreCase(message.getSender())) {
                    // Trim the bot message to the specified word limit
                    String[] words = message.getMessage().split("\\s+");
                    if (words.length > botWordLimit) {
                        String trimmedText = String.join(" ", Arrays.copyOfRange(words, 0, botWordLimit));
                        trimmedMessage.setMessage(trimmedText);
                    } else {
                        trimmedMessage.setMessage(message.getMessage());
                    }
                } else {
                    // Keep full user messages
                    trimmedMessage.setMessage(message.getMessage());
                }

                trimmedMessages.add(trimmedMessage);
            }

        } catch (Exception e) {
            Logger.getLogger(ChatHistoryService.class.getName())
                    .log(Level.SEVERE, "Failed to retrieve trimmed messages for memory", e);
        }

        return trimmedMessages;
    }
}