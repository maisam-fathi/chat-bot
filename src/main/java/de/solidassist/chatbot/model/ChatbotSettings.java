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

    // Profile name associated with the chatbot settings
    private String profileName;

    // URL of the LLM server
    private String llmServerUrl;

    // Provider of the LLM (e.g., OpenAI, Ollama, etc.)
    private String llmProvider;

    // Name of the LLM model (e.g., gpt-4, llama2, etc.)
    private String llmModelName;

    // Access token for the model (optional)
    private String modelAccessToken = "";

    // A path for reference documents related to the model (optional)
    private String referenceFilePath = "";

    // Maximum tokens limit in percent (0-100)
    private int maxTokensPercent;

    // Temperature for content generation in percent (0-100)
    private int temperaturePercent;

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
     * @param id Unique identifier for the chatbot settings record.
     * @param profileName Profile name associated with the chatbot settings.
     * @param llmServerUrl URL of the LLM server.
     * @param llmProvider Provider of the LLM.
     * @param llmModelName Name of the LLM model.
     * @param modelAccessToken Access token for the model.
     * @param referenceFilePath Path for reference documents related to the model.
     * @param maxTokensPercent Maximum tokens limit in percent (0-100).
     * @param temperaturePercent Temperature for content generation in percent (0-100).
     * @param updatedAt Timestamp of the last update.
     */
    public ChatbotSettings(int id, String profileName, String llmServerUrl, String llmProvider, String llmModelName,
                           String modelAccessToken, String referenceFilePath,
                           int maxTokensPercent, int temperaturePercent, Date updatedAt) {
        this.id = id;
        this.profileName = profileName != null ? profileName : "";
        this.llmServerUrl = llmServerUrl;
        this.llmProvider = llmProvider;
        this.llmModelName = llmModelName;
        this.modelAccessToken = modelAccessToken != null ? modelAccessToken : "";
        this.referenceFilePath = referenceFilePath != null ? referenceFilePath : "";
        this.maxTokensPercent = maxTokensPercent;
        this.temperaturePercent = temperaturePercent;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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

    public String getModelAccessToken() {
        return modelAccessToken;
    }

    public void setModelAccessToken(String modelAccessToken) {
        this.modelAccessToken = modelAccessToken;
    }

    public String getReferenceFilePath() {
        return referenceFilePath;
    }

    public void setReferenceFilePath(String referenceFilePath) {
        this.referenceFilePath = referenceFilePath;
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
                ", profileName='" + profileName + '\'' +
                ", llmServerUrl='" + llmServerUrl + '\'' +
                ", llmProvider='" + llmProvider + '\'' +
                ", llmModelName='" + llmModelName + '\'' +
                ", modelAccessToken='" + modelAccessToken + '\'' +
                ", referenceFilePath='" + referenceFilePath + '\'' +
                ", maxTokensPercent=" + maxTokensPercent +
                ", temperaturePercent=" + temperaturePercent +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
