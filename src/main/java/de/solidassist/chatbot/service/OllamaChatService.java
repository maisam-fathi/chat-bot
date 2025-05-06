package de.solidassist.chatbot.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling chat interactions using a language model.
 * <p>
 * This class delegates chat messages to the underlying {@link ChatLanguageModel}
 * and returns the generated response. It is annotated as a Spring {@code @Service},
 * making it a candidate for component scanning and dependency injection.
 */
@Service
public class OllamaChatService {

    /**
     * The language model used to generate chat responses.
     */
    private final ChatLanguageModel model;

    /**
     * Constructs a new {@code OllamaChatService} with the provided language model.
     *
     * @param model the {@link ChatLanguageModel} used for generating responses
     */
    public OllamaChatService(ChatLanguageModel model) {
        this.model = model;
    }

    /**
     * Sends a message to the language model and returns the generated response.
     *
     * @param message the input message to send to the model
     * @return the model's generated response
     */
    public String chat(String message) {
        return model.generate(message);
    }
}
