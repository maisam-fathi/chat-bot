package de.solidassist.chatbot.dependency;

import dev.langchain4j.model.ollama.OllamaChatModel;
/**
 * This class tests the integration of the LangChain4j dependency with Ollama.
 * It ensures that the LangChain4j library can send a request to the Ollama model
 * and receive a valid response. The expected behavior is that the model will
 * provide a simple, correct answer to a general knowledge question (e.g.,
 * "What is the capital of Germany?").
 *
 * Expected result:
 * - The response from the Ollama model should be correctly processed and printed.
 *
 * Dependencies tested:
 * - LangChain4j (dev.langchain4j:langchain4j-ollama)
 */

public class LangChain4jTest {
    public static void main(String[] args) {
        // Create Ollama chat model connected to localhost (default model: llama2)
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama2") // or any model you have installed in Ollama
                .build();

        // Send simple prompt and print response
        String response = model.chat("What is the capital of Germany?");
        System.out.println("Ollama response: " + response);
    }
}
