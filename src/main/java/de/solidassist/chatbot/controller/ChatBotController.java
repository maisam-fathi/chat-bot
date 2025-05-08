package de.solidassist.chatbot.controller;

import de.solidassist.chatbot.service.OllamaChatService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

/**
 * Controller class responsible for initializing and providing access to chatbot services.
 * <p>
 * This class separates the initialization logic of the language model and chatbot service
 * from the UI layer, in alignment with the MVC design pattern.
 */
public class ChatBotController {

    /**
     * Initializes the {@link OllamaChatService} using a predefined base URL and model name.
     * <p>
     * Note: This method currently uses hardcoded values. A future implementation should
     * retrieve these settings dynamically from a configuration or settings window.
     *
     * @return an initialized instance of {@link OllamaChatService}
     */
    public static OllamaChatService initChatService() {
        // TODO: Later read baseUrl and modelName from settings instead of hardcoding
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama2")
                .build();
        return new OllamaChatService(model);
    }
}
