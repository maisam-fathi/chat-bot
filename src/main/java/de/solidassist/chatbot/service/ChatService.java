package de.solidassist.chatbot.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling chat interactions using a language model.
 * <p>
 * This class delegates chat messages to the underlying {@link ChatLanguageModel}
 * and returns the generated response. It is annotated as a Spring {@code @Service},
 * making it a candidate for component scanning and dependency injection.
 */
@Service
public class ChatService {

    /**
     * The language model used to generate chat responses.
     */
    private final ChatLanguageModel model;

    /**
     * Constructs a new {@code ChatService} with the provided language model.
     *
     * @param model the {@link ChatLanguageModel} used for generating responses
     */
    public ChatService(ChatLanguageModel model) {
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

    /**
     * Sends a message to the language model along with memory (chat history) and returns the response.
     *
     * @param memoryMessages List of previous chat messages (formatted for LangChain4j)
     * @param userInput      New user input message
     * @return Generated model response
     */
    public String chatWithMemory(List<ChatMessage> memoryMessages, String userInput) {
        List<ChatMessage> fullConversation = new ArrayList<>(memoryMessages);

        // Add the latest user message to the conversation
        fullConversation.add(UserMessage.from(userInput));

        // Generate a response using the full context
        return model.generate(fullConversation).content().text();
    }

    /**
     * Converts trimmed ChatMessage objects to LangChain4j ChatMessage instances.
     * <p>
     * It simply maps the internal model to LangChain-compatible format.
     *
     * @param trimmedMessages The list of internal ChatMessage objects (already trimmed).
     * @return List of LangChain4j-compatible ChatMessage objects.
     */
    public List<dev.langchain4j.data.message.ChatMessage> convertToLangChainMessages(
            List<de.solidassist.chatbot.model.ChatMessage> trimmedMessages) {

        List<dev.langchain4j.data.message.ChatMessage> result = new ArrayList<>();

        for (de.solidassist.chatbot.model.ChatMessage msg : trimmedMessages) {
            if ("user".equalsIgnoreCase(msg.getSender())) {
                result.add(UserMessage.from(msg.getMessage()));
            } else if ("bot".equalsIgnoreCase(msg.getSender())) {
                result.add(AiMessage.from(msg.getMessage()));
            }
        }
        return result;
    }
}
