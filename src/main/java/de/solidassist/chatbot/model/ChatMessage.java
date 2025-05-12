package de.solidassist.chatbot.model;

import java.util.Date;

/**
 * The ChatMessage class represents a record in the chat_messages table.
 * It is a POJO (Plain Old Java Object) used to map data from the database to a Java object.
 * This class contains the fields corresponding to the columns in the chat_messages table.
 */
public class ChatMessage {

    // Unique identifier for the chat message (Primary Key)
    private int id;

    // Foreign key that links to the chat_sessions table (session_id)
    private int sessionId;

    // Sender of the message, either 'user' or 'bot'
    private String sender;

    // The content of the chat message
    private String message;

    // The timestamp when the message was created
    private Date createdAt;

    /**
     * Default constructor.
     */
    public ChatMessage() {
    }

    /**
     * Constructor to initialize a ChatMessage with sessionId, sender, message, and createdAt.
     *
     * @param sessionId The session ID that links to a specific chat session.
     * @param sender The sender of the message ('user' or 'bot').
     * @param message The content of the chat message.
     * @param createdAt The creation timestamp of the chat message.
     */
    public ChatMessage(int sessionId, String sender, String message, Date createdAt) {
        this.sessionId = sessionId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    /**
     * Gets the ID of the chat message.
     *
     * @return The ID of the chat message.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the chat message.
     *
     * @param id The ID of the chat message.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the session ID that the message belongs to.
     *
     * @return The session ID.
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID that the message belongs to.
     *
     * @param sessionId The session ID.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the sender of the message.
     *
     * @return The sender of the message ('user' or 'bot').
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender The sender of the message ('user' or 'bot').
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the content of the chat message.
     *
     * @return The content of the chat message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the chat message.
     *
     * @param message The content of the chat message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the creation timestamp of the chat message.
     *
     * @return The creation timestamp of the chat message.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the chat message.
     *
     * @param createdAt The creation timestamp of the chat message.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
