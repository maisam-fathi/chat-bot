package de.solidassist.chatbot;

import de.solidassist.chatbot.model.ChatMessage;
import de.solidassist.chatbot.service.ChatHistoryService;

import java.util.List;

public class ChatHistoryTest {
    public static void main(String[] args) {
        ChatHistoryService service = new ChatHistoryService();

        // Sample test parameters
        int sessionId = 81;        // Use a valid session ID from your DB
        int messageLimit = 15;    // Last 15 messages
        int botWordLimit = 130;    // Max 130 words for bot messages

        List<ChatMessage> messages = service.getTrimmedMessagesForMemory(sessionId, messageLimit, botWordLimit);

        System.out.println("Trimmed messages for memory:");
        for (ChatMessage msg : messages) {
            System.out.println("[" + msg.getSender() + "] " + msg.getMessage());
            System.out.println("Words count: " + msg.getMessage().split("\\s+").length);
        }
    }
}