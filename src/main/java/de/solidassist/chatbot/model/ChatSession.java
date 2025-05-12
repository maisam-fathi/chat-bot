package de.solidassist.chatbot.model;

import java.util.Date;

/**
 * The ChatSession class represents a record in the chat_sessions table.
 * It is a POJO (Plain Old Java Object) used to map data from the database to a Java object.
 * This class contains the fields corresponding to the columns in the chat_sessions table.
 */
public class ChatSession {

    // Unique identifier for the chat session (Primary Key)
    private int id;

    // Name of the chat session
    private String sessionName;

    // Date and time when the chat session was created
    private Date createdAt;

    /**
     * Default constructor.
     */
    public ChatSession() {
    }

    /**
     * Constructor to initialize a ChatSession with session name and creation time.
     *
     * @param sessionName The name of the chat session.
     * @param createdAt The creation timestamp of the chat session.
     */
    public ChatSession(String sessionName, Date createdAt) {
        this.sessionName = sessionName;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    /**
     * Gets the ID of the chat session.
     *
     * @return The ID of the chat session.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the chat session.
     *
     * @param id The ID of the chat session.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the chat session.
     *
     * @return The name of the chat session.
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Sets the name of the chat session.
     *
     * @param sessionName The name of the chat session.
     */
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    /**
     * Gets the creation timestamp of the chat session.
     *
     * @return The creation timestamp of the chat session.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the chat session.
     *
     * @param createdAt The creation timestamp of the chat session.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ChatSession{" +
                "id=" + id +
                ", sessionName='" + sessionName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
