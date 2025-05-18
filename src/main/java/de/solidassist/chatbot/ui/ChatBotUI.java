package de.solidassist.chatbot.ui;

import de.solidassist.chatbot.service.OllamaChatService;
import de.solidassist.chatbot.controller.ChatBotController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.solidassist.chatbot.service.ChatHistoryService;
import de.solidassist.chatbot.model.ChatSession;
import de.solidassist.chatbot.model.ChatMessage;

import java.sql.SQLException;
import java.util.List;

public class ChatBotUI {

    private static final Logger logger = Logger.getLogger(ChatBotUI.class.getName());
    private static OllamaChatService chatService;
    private static int currentSessionId = -1; // Keep track of the current chat session ID
    private static final ChatHistoryService chatHistoryService = new ChatHistoryService();

    public static void main(String[] args) {
        // Initialize the chatbot service
        chatService = ChatBotController.initChatService();

        // Launch the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(ChatBotUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main application window
        JFrame frame = new JFrame("AI Chatbot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); // Center the window on screen
        frame.setLayout(new BorderLayout(5, 5)); // Add padding between elements

        // === ROW 1: Chat history panel (left) and chat display (right) ===
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        // Left: History list with scroll pane
        JList<String> historyList = new JList<>();
        DefaultListModel<String> historyListModel = new DefaultListModel<>();
        historyList.setModel(historyListModel);
        // Load chat history from the database when GUI loads
        try {
            List<ChatSession> sessions = chatHistoryService.getAllSessions();
            for (ChatSession session : sessions) {
                historyListModel.addElement(session.getSessionName() + " (ID: " + session.getId() + ")");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to load chat history", e);
        }
        JScrollPane historyScroll = new JScrollPane(historyList);
        historyScroll.setPreferredSize(new Dimension(180, 0));
        topPanel.add(historyScroll, BorderLayout.WEST);

        // Right: Chat display area (read-only)
        JTextPane chatDisplay = new JTextPane();
        chatDisplay.setEditable(false);

        // Apply custom font and paragraph style for better readability
        Style defaultStyle = chatDisplay.addStyle("default", null);
        StyleConstants.setFontFamily(defaultStyle, "SansSerif");
        StyleConstants.setFontSize(defaultStyle, 16);
        StyleConstants.setLineSpacing(defaultStyle, 0.2f); // More space between lines
        StyleConstants.setSpaceAbove(defaultStyle, 0);     // More space above each message
        StyleConstants.setSpaceBelow(defaultStyle, 0);     // More space below each message

        chatDisplay.setParagraphAttributes(defaultStyle, true);

        JScrollPane chatScroll = new JScrollPane(chatDisplay);
        topPanel.add(chatScroll, BorderLayout.CENTER);

        // Add the top panel to the center of the frame
        frame.add(topPanel, BorderLayout.CENTER);

        // === ROW 2: User input field and Send button ===
        JPanel middlePanel = new JPanel(new BorderLayout(5, 5));
        middlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JTextField inputField = new JTextField(); // User types here
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JButton sendButton = new JButton("Send"); // Send button

        middlePanel.add(inputField, BorderLayout.CENTER);
        middlePanel.add(sendButton, BorderLayout.EAST);

        // === ROW 3: Bottom controls ===
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Left: Settings, Copy Chat, New Chat buttons
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton settingsButton = new JButton("Settings");
        JButton copyButton = new JButton("Copy Chat");
        JButton newChatButton = new JButton("New Chat");
        JButton deleteChatButton = new JButton("Delete Chat");

        leftButtons.add(settingsButton);
        leftButtons.add(copyButton);
        leftButtons.add(newChatButton);
        leftButtons.add(deleteChatButton);

        // Right: Send on Enter checkbox
        JCheckBox sendOnEnterCheck = new JCheckBox("Send on Enter", true);
        JPanel rightCheckbox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightCheckbox.add(sendOnEnterCheck);

        bottomPanel.add(leftButtons, BorderLayout.WEST);
        bottomPanel.add(rightCheckbox, BorderLayout.EAST);

        // === Container for both middle (input) and bottom (buttons) rows ===
        JPanel bottomContainer = new JPanel(new GridLayout(2, 1));
        bottomContainer.add(middlePanel); // Input field and Send button
        bottomContainer.add(bottomPanel); // Control buttons and checkbox

        frame.add(bottomContainer, BorderLayout.SOUTH); // Add to bottom of window

        // === Send Button Action ===
        sendButton.addActionListener(e -> {
            String userText = inputField.getText().trim();
            if (!userText.isEmpty()) {
                // Scrolling the chat window down
                chatDisplay.setCaretPosition(chatDisplay.getDocument().getLength());
                // Disable the Send button and change its label to "Waiting"
                sendButton.setEnabled(false);
                sendButton.setText("Waiting");

                // Display the user's message in the chat area
                appendChat(chatDisplay, "\n🙂: " + userText + "\n", Color.BLUE);
                inputField.setText(""); // Clear the input field

                // Save the starting position of the 'typing...' message
                int typingStart = chatDisplay.getDocument().getLength();

                // Temporary 'typing...' message from the chatbot
                appendChat(chatDisplay, "\n🤖: typing...\n", Color.DARK_GRAY);

                // Check if a session exists, create new if not
                if (currentSessionId == -1) {
                    try {
                        currentSessionId = chatHistoryService.createSession("New Chat");
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Failed to create new chat session", ex);
                        appendChat(chatDisplay, "\n⚠️ Failed to create chat session\n", Color.RED);
                        sendButton.setEnabled(true);
                        sendButton.setText("Send");
                        return;
                    }
                }

                // Save user message to the database
                try {
                    chatHistoryService.addMessageToSession(currentSessionId, "user", userText);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Failed to save user message", ex);
                }

                // Fetch chatbot's response asynchronously
                getChatbotResponse(userText, chatbotResponse -> {
                    try {
                        // Remove the 'typing...' message
                        chatDisplay.getDocument().remove(typingStart, "🤖: typing...\n".length() + 1);
                    } catch (BadLocationException ex) {
                        logger.log(Level.SEVERE, "Failed to remove typing message", ex);
                    }

                    // Append the actual chatbot response
                    appendChat(chatDisplay, "\n🤖: " + chatbotResponse.trim() + "\n", Color.BLACK);

                    // Save bot message to the database
                    try {
                        chatHistoryService.addMessageToSession(currentSessionId, "bot", chatbotResponse);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Failed to save bot message", ex);
                    }

                    // Re-enable the Send button and restore its label to "Send"
                    sendButton.setEnabled(true);
                    sendButton.setText("Send");

                    // Estimate the position near the start of the LLM response
                    int offset = Math.max(chatDisplay.getDocument().getLength() - (chatbotResponse.length() / 50) * 50, 0);
                    chatDisplay.setCaretPosition(offset);
                });
            }
        });

        // === Copy Chat Action ===
        copyButton.addActionListener(e -> {
            // Copy the entire chat display content to clipboard
            String text = chatDisplay.getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        });

        // === New Chat Button Action ===
        newChatButton.addActionListener(e -> createNewChatAndSelect(historyListModel, historyList, chatDisplay));

        // === Delete Chat Button Action ===
        deleteChatButton.addActionListener(e -> {
            deleteCurrentSession(historyListModel, historyList, chatDisplay);
        });

        // === Handle the Enter key behavior depending on checkbox ===
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean sendOnEnter = sendOnEnterCheck.isSelected();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (sendOnEnter) {
                        sendButton.doClick(); // Simulate send button click
                        e.consume(); // Prevent newline
                    }
                }
            }
        });

        // === Settings Button Action ===
        settingsButton.addActionListener(e -> {
            // Open the Settings window when the button is clicked
            SettingsWindow settingsWindow = new SettingsWindow(frame); // 'frame' is your main window
            settingsWindow.setVisible(true); // Show the settings window
        });

        // Add a session selection listener to the history list
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = historyList.getSelectedValue();
                if (selectedValue != null) {
                    try {
                        int idStart = selectedValue.lastIndexOf("(ID: ") + 5;
                        int idEnd = selectedValue.lastIndexOf(")");
                        int sessionId = Integer.parseInt(selectedValue.substring(idStart, idEnd));

                        // Reset the current session ID to the selected one
                        currentSessionId = sessionId;

                        // Fetch chat messages for the selected session using the available method
                        List<ChatMessage> messages = chatHistoryService.getMessagesForSession(sessionId);

                        // Clear the current chat display
                        chatDisplay.setText("");

                        // Display all messages
                        for (ChatMessage message : messages) {
                            String prefix = message.getSender().equalsIgnoreCase("user") ? "🙂: " : "🤖: ";
                            Color color = message.getSender().equalsIgnoreCase("user") ? Color.BLUE : Color.BLACK;
                            appendChat(chatDisplay, prefix + message.getMessage() + "\n", color);
                        }

                    } catch (Exception ex) {
                        logger.log(Level.WARNING, "Failed to load chat messages for selected session", ex);
                    }
                }
            }
        });

        // === Auto-create new chat on the first load ===
        createNewChatAndSelect(historyListModel, historyList, chatDisplay);

        // Show the window
        frame.setVisible(true);
    }

    private static void appendChat(JTextPane pane, String text, Color color) {
        try {
            var doc = pane.getStyledDocument();
            var style = pane.addStyle("Style", null);
            StyleConstants.setForeground(style, color);
            doc.insertString(doc.getLength(), text, style);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error appending chat message", e);
        }
    }

    private static void getChatbotResponse(String userText, java.util.function.Consumer<String> callback) {
        new Thread(() -> {
            try {
                // Use the OllamaChatService to fetch the chatbot response
                String response = chatService.chat(userText);

                // Pass the response to the callback without saving it here
                SwingUtilities.invokeLater(() -> callback.accept(response));
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to fetch chatbot response", e);
                SwingUtilities.invokeLater(() -> callback.accept("Sorry, an error occurred while fetching the response."));
            }
        }).start();
    }

    // === Create and Select New Chat Session ===
    private static void createNewChatAndSelect(DefaultListModel<String> historyListModel, JList<String> historyList, JTextPane chatDisplay) {
        try {
            // Create a new session and keep its ID
            int newSessionId = chatHistoryService.createSession("New Chat");
            currentSessionId = newSessionId;

            // Clear chat display
            chatDisplay.setText("");

            // Refresh the list
            historyListModel.clear();
            List<ChatSession> sessions = chatHistoryService.getAllSessions();
            for (ChatSession session : sessions) {
                historyListModel.addElement(session.getSessionName() + " (ID: " + session.getId() + ")");
            }

            // Find the index of the newly created session and select it
            for (int i = 0; i < historyListModel.size(); i++) {
                String item = historyListModel.get(i);
                if (item.contains("(ID: " + newSessionId + ")")) {
                    historyList.setSelectedIndex(i);
                    break;
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to create new chat session", ex);
            appendChat(chatDisplay, "\n Failed to create new chat session\n", Color.RED);
        }
    }

    /**
     * Deletes the current chat session from the database and updates the UI.
     *
     * @param historyListModel The list model for chat history.
     * @param historyList      The UI component displaying chat history.
     * @param chatDisplay      The chat display area.
     */
    private static void deleteCurrentSession(DefaultListModel<String> historyListModel, JList<String> historyList, JTextPane chatDisplay) {
        if (currentSessionId == -1) {
            JOptionPane.showMessageDialog(null, "Please select a chat session to delete.", "No Session Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this chat session?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            boolean success = chatHistoryService.deleteSessionWithMessages(currentSessionId);

            if (success) {
                currentSessionId = -1;
                chatDisplay.setText("");

                // Refresh the history list
                historyListModel.clear();
                List<ChatSession> sessions = chatHistoryService.getAllSessions();
                for (ChatSession session : sessions) {
                    historyListModel.addElement(session.getSessionName() + " (ID: " + session.getId() + ")");
                }

                // Auto-create a new chat session for smooth UX
                createNewChatAndSelect(historyListModel, historyList, chatDisplay);

                JOptionPane.showMessageDialog(null, "Chat session deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete chat session.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting chat session", ex);
            JOptionPane.showMessageDialog(null, "Error deleting chat session.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}