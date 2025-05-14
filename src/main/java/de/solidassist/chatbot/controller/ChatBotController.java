package de.solidassist.chatbot.controller;

import de.solidassist.chatbot.dao.ChatbotSettingsDAO;
import de.solidassist.chatbot.model.ChatbotSettings;
import de.solidassist.chatbot.service.OllamaChatService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.logging.Level;
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
     * Loads chatbot settings from the database.
     * Falls back to default settings if loading fails.
     */
    private static void loadSettings() {
        try {
            currentSettings = chatbotSettingsDAO.getSettingsById(1);
            if (currentSettings == null) {
                logger.info("No settings found in database. Using default settings.");
                currentSettings = getDefaultSettings();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load chatbot settings from database. Using defaults.", e);
            currentSettings = getDefaultSettings();
        }

        // Print loaded settings for debugging
        logger.info("Loaded settings: " + currentSettings.toString());
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
        defaults.setLanguage("en");
        return defaults;
    }

    /**
     * Initializes the {@link OllamaChatService} using loaded settings.
     *
     * @return an initialized instance of {@link OllamaChatService}
     */
    public static OllamaChatService initChatService() {
        if (currentSettings == null) {
            loadSettings();
        }
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(currentSettings.getLlmServerUrl())
                .modelName(currentSettings.getLlmModelName())
                .temperature(currentSettings.getTemperaturePercent() / 100.0)
                .numPredict((1000 * currentSettings.getMaxTokensPercent()) / 100)
                .build();
        return new OllamaChatService(model);
    }
}