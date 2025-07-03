package de.solidassist.chatbot;

import javax.swing.SwingUtilities;
import de.solidassist.chatbot.ui.ChatBotUI;

public class ChatBotApplication {

    public static void main(String[] args) {
        // Run the Swing GUI
        SwingUtilities.invokeLater(ChatBotUI::createAndShowGUI);
    }
}