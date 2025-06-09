package de.solidassist.chatbot.controller;

import de.solidassist.chatbot.service.ChatService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to handle chat messages.
 * This controller receives user input via POST requests,
 * sends the message to the language model via the service layer,
 * and returns the generated response.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    /**
     * Constructor injection for the chat service.
     *
     * @param chatService service that connects to the language model
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Endpoint to handle user messages.
     * Accepts a plain text message and returns the model's response.
     *
     * @param userMessage the user's input message
     * @return the generated response from the language model
     */
    @PostMapping
    public String chat(@RequestBody String userMessage) {
        return chatService.chat(userMessage);
    }
}