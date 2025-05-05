package de.solidassist.chatbot.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService {
    private final ChatLanguageModel model;

    public OllamaChatService(ChatLanguageModel model) {
        this.model = model;
    }

    public String chat(String message) {
        return model.generate(message);
    }
}