package de.solidassist.chatbot.model;

import java.util.Date;

/**
 * The ChatbotSettings class represents a record in the chatbot_settings table.
 * It is a POJO (Plain Old Java Object) used to map data from the database to a Java object.
 * This class contains the fields corresponding to the columns in the chatbot_settings table.
 */
public class ChatbotSettings {

    // Unique identifier for the chatbot settings record (Primary Key)
    private int id;

    // URL of the LLM server
    private String llmServerUrl;

    // Provider of the LLM (e.g., OpenAI, Ollama, etc.)
    private String llmProvider;

    // Name of the LLM model (e.g., gpt-4, llama2, etc.)
    private String llmModelName;

    // Maximum tokens limit in percent (0-100)
    private int maxTokensPercent;

    // Temperature for content generation in percent (0-100)
    private int temperaturePercent;

    // Language code of the chatbot responses (e.g., en, de, etc.)
    private String language;

    // Timestamp when the settings were last updated
    private Date updatedAt;

    /**
     * Default constructor.
     */
    public ChatbotSettings() {
    }

    /**
     * Constructor to initialize all chatbot settings fields.
     *
     * @param llmServerUrl URL of the LLM server.
     * @param llmProvider Provider of the LLM.
     * @param llmModelName Name of the LLM model.
     * @param maxTokensPercent Maximum tokens limit in percent.
     * @param temperaturePercent Temperature for content generation in percent.
     * @param language Language code for responses.
     * @param updatedAt Timestamp of the last update.
     */
    public ChatbotSettings(String llmServerUrl, String llmProvider, String llmModelName,
                           int maxTokensPercent, int temperaturePercent, String language, Date updatedAt) {
        this.llmServerUrl = llmServerUrl;
        this.llmProvider = llmProvider;
        this.llmModelName = llmModelName;
        this.maxTokensPercent = maxTokensPercent;
        this.temperaturePercent = temperaturePercent;
        this.language = language;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLlmServerUrl() {
        return llmServerUrl;
    }

    public void setLlmServerUrl(String llmServerUrl) {
        this.llmServerUrl = llmServerUrl;
    }

    public String getLlmProvider() {
        return llmProvider;
    }

    public void setLlmProvider(String llmProvider) {
        this.llmProvider = llmProvider;
    }

    public String getLlmModelName() {
        return llmModelName;
    }

    public void setLlmModelName(String llmModelName) {
        this.llmModelName = llmModelName;
    }

    public int getMaxTokensPercent() {
        return maxTokensPercent;
    }

    public void setMaxTokensPercent(int maxTokensPercent) {
        this.maxTokensPercent = maxTokensPercent;
    }

    public int getTemperaturePercent() {
        return temperaturePercent;
    }

    public void setTemperaturePercent(int temperaturePercent) {
        this.temperaturePercent = temperaturePercent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ChatbotSettings{" +
                "id=" + id +
                ", llmServerUrl='" + llmServerUrl + '\'' +
                ", llmProvider='" + llmProvider + '\'' +
                ", llmModelName='" + llmModelName + '\'' +
                ", maxTokensPercent=" + maxTokensPercent +
                ", temperaturePercent=" + temperaturePercent +
                ", language='" + language + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
