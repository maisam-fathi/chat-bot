package de.solidassist.chatbot;

import java.sql.*;
import java.util.*;

import de.solidassist.chatbot.dao.*;
import de.solidassist.chatbot.model.*;
import de.solidassist.chatbot.util.DatabaseConnection;

public class DatabaseCRUDTest {
    public static void main(String[] args) {
        ChatSessionDAO sessionDAO = new ChatSessionDAO();
        ChatMessageDAO messageDAO = new ChatMessageDAO();
        ChatbotSettingsDAO settingsDAO = new ChatbotSettingsDAO();

        try (Scanner scanner = new Scanner(System.in)) {
            // Test ChatSession
            System.out.println("Enter ChatSession name for testing CRUD: ");
            String sessionName = scanner.nextLine();
            ChatSession newSession = new ChatSession();
            newSession.setSessionName(sessionName);
            int sessionId = sessionDAO.insert(newSession);
            System.out.println("✅ ChatSession inserted with ID: " + sessionId);

            ChatSession fetchedSession = sessionDAO.getById(sessionId);
            System.out.println("🔍 Fetched ChatSession: " + fetchedSession.getSessionName());

            System.out.println("Enter new name for ChatSession (" + fetchedSession.getSessionName() + ") to update: ");
            String newSessionName = scanner.nextLine();
            fetchedSession.setSessionName(newSessionName);
            sessionDAO.update(fetchedSession);
            System.out.println("✏️ ChatSession updated.");

            System.out.println("Do you want to delete ChatSession with ID " + sessionId + "? [y/n]");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                Connection conn = DatabaseConnection.getInstance().getConnection();
                sessionDAO.delete(conn, sessionId);
                System.out.println("🗑️ ChatSession deleted.");
            }

            // Test ChatMessage
            System.out.println("Enter existing ChatSession ID for ChatMessage (must exist in DB): ");
            int existingSessionId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter ChatMessage content for testing CRUD: ");
            String messageContent = scanner.nextLine();

            ChatMessage newMessage = new ChatMessage();
            newMessage.setSessionId(existingSessionId);
            newMessage.setSender("user");
            newMessage.setMessage(messageContent);
            int messageId = messageDAO.insert(newMessage);
            System.out.println("✅ ChatMessage inserted with ID: " + messageId);

            List<ChatMessage> messages = messageDAO.getMessagesBySessionId(existingSessionId);
            System.out.println("🔍 Fetched ChatMessages for sessionId " + existingSessionId + ":");
            for (ChatMessage msg : messages) {
                System.out.println("- ID: " + msg.getId() + " | Message: " + msg.getMessage());
            }

            System.out.println("Enter ChatMessage ID to delete: ");
            int deleteMessageId = scanner.nextInt();
            scanner.nextLine(); /*N*/
            System.out.println("Do you want to delete ChatMessage with ID " + deleteMessageId + "? [y/n]");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                messageDAO.deleteById(deleteMessageId);
                System.out.println("🗑️ ChatMessage deleted.");
            }

            // Test ChatbotSettings
            System.out.println("Enter LLM model name for testing CRUD: ");
            String llmModelName = scanner.nextLine();

            ChatbotSettings settings = new ChatbotSettings();
            settings.setLlmServerUrl("http://localhost:5000");
            settings.setLlmProvider("OpenAI");
            settings.setLlmModelName(llmModelName);
            settings.setMaxTokensPercent(100);
            settings.setTemperaturePercent(75);
            settings.setLanguage("en");
            int settingsId = settingsDAO.insertSettings(settings);
            System.out.println("✅ ChatbotSettings inserted with ID: " + settingsId);

            ChatbotSettings fetchedSettings = settingsDAO.getSettingsById(settingsId);
            System.out.println("🔍 Fetched ChatbotSettings: " + fetchedSettings.getLlmModelName());

            System.out.println("Enter new temperature percent for ChatbotSettings update: ");
            int newTemperature = scanner.nextInt();
            scanner.nextLine();
            fetchedSettings.setTemperaturePercent(newTemperature);
            settingsDAO.updateSettings(fetchedSettings);
            System.out.println("✏️ ChatbotSettings updated.");

            System.out.println("Do you want to delete ChatbotSettings with ID " + settingsId + "? [y/n]");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                settingsDAO.deleteSettings(settingsId);
                System.out.println("🗑️ ChatbotSettings deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}