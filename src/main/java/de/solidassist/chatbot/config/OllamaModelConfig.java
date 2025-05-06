package de.solidassist.chatbot.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the {@link ChatLanguageModel} using Ollama.
 * <p>
 * This class reads model configuration properties from the application's configuration
 * (e.g., {@code application.properties} or {@code application.yml}) and defines a
 * {@code ChatLanguageModel} bean for use in the application.
 */
@Configuration
public class OllamaModelConfig {

    /**
     * The base URL of the Ollama API, injected from configuration properties.
     */
    @Value("${ollama.base-url}")
    private String baseUrl;

    /**
     * The name of the model to use, injected from configuration properties.
     */
    @Value("${ollama.model-name}")
    private String modelName;

    /**
     * Creates and configures a {@link ChatLanguageModel} bean using the Ollama API.
     * <p>
     * This bean will be available for dependency injection throughout the Spring application.
     *
     * @return an instance of {@link ChatLanguageModel} configured with Ollama settings
     */
    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }
}
