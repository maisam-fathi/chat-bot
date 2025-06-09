package de.solidassist.chatbot.controller;

import de.solidassist.chatbot.dao.ChatbotSettingsDAO;
import de.solidassist.chatbot.model.ChatbotSettings;
import de.solidassist.chatbot.service.ChatService;
import de.solidassist.chatbot.util.AppPreferenceUtils;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Controller class responsible for initializing and providing access to chatbot services.
 * <p>
 * This class loads chatbot settings from the database at startup
 * and uses them to initialize the chat service.
 */
public class ChatBotController {

    private static final Logger logger = Logger.getLogger(ChatBotController.class.getName());
    private static final ChatbotSettingsDAO chatbotSettingsDAO = new ChatbotSettingsDAO();

    private static ChatbotSettings currentSettings;

    /**
     * Loads the chatbot settings for the given profile ID.
     * If the profileId is -1, it attempts to load the last selected profile ID from preferences.
     * If that fails, it loads the default settings.
     *
     * @param profileId the ID of the settings profile to load, or -1 to auto-detect
     */
    public static void loadSettings(int profileId) throws SQLException {
        if (profileId == 0) {
            String lastSelectedIdStr = AppPreferenceUtils.loadPreference("lastSelectedProfileId");
            if (lastSelectedIdStr != null) {
                try {
                    profileId = Integer.parseInt(lastSelectedIdStr);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid lastSelectedProfileId in preferences: " + lastSelectedIdStr);
                    profileId = 0;
                }
            }
        }

        if (profileId != 0) {
            currentSettings = chatbotSettingsDAO.getSettingsById(profileId);
            if (currentSettings == null) {
                logger.info("No settings found for profileId: " + profileId + ", falling back to default.");
                currentSettings = getDefaultSettings();
            }
        } else {
            currentSettings = getDefaultSettings();
        }
    }

    /**
     * Provides default settings when database settings are unavailable.
     *
     * @return default ChatbotSettings
     */
    private static ChatbotSettings getDefaultSettings() {
        ChatbotSettings defaults = new ChatbotSettings();
        defaults.setLlmServerUrl("http://localhost:11434");
        defaults.setLlmProvider("ollama");
        defaults.setLlmModelName("llama3");
        defaults.setMaxTokensPercent(100);
        defaults.setTemperaturePercent(70);
        return defaults;
    }

    /**
     * Initializes the {@link ChatService} using loaded settings.
     *
     * @return an initialized instance of {@link ChatService}
     */
    public static ChatService initChatService() throws SQLException {
        if (currentSettings == null) {
            loadSettings(getDefaultSettings().getId());
        }
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(currentSettings.getLlmServerUrl())
                .modelName(currentSettings.getLlmModelName())
                .temperature(currentSettings.getTemperaturePercent() / 100.0)
                .numPredict((1000 * currentSettings.getMaxTokensPercent()) / 100)
                .build();
        logger.info("ChatService initialized with model: " +
                "baseUrl=" + currentSettings.getLlmServerUrl() +
                ", modelName=" + currentSettings.getLlmModelName() +
                ", temperature=" + (currentSettings.getTemperaturePercent() / 100.0) +
                ", maxTokens=" + ((1000 * currentSettings.getMaxTokensPercent()) / 100));
        return new ChatService(model);
    }

    /**
     * Sets the current ChatbotSettings profile globally.
     *
     * @param settings The selected ChatbotSettings object.
     */
    public static void setCurrentSettings(ChatbotSettings settings) {
        currentSettings = settings;
    }
}