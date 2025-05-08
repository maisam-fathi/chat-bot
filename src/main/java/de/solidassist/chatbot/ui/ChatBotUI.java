package de.solidassist.chatbot.ui;

import de.solidassist.chatbot.service.OllamaChatService;
import de.solidassist.chatbot.controller.ChatBotController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatBotUI {

    private static final Logger logger = Logger.getLogger(ChatBotUI.class.getName());
    private static OllamaChatService chatService;

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

        // Left: History list with scroll pane (1/5 of the total window width)
        JList<String> historyList = new JList<>();
        JScrollPane historyScroll = new JScrollPane(historyList);
        historyScroll.setPreferredSize(new Dimension(180, 0)); // Approx. 1/5 of 900px
        // TODO: Load chat history from database and group by date range
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

        leftButtons.add(settingsButton);
        leftButtons.add(copyButton);
        leftButtons.add(newChatButton);

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

                // Fetch chatbot's response asynchronously
                getChatbotResponse(userText, chatbotResponse -> {
                    try {
                        // Remove the 'typing...' message
                        chatDisplay.getDocument().remove(typingStart, "🤖: typing...\n".length() + 1);
                    } catch (BadLocationException ex) {
                        logger.log(Level.SEVERE, "Failed to remove typing message", ex);
                    }

                    // Append the actual chatbot response
                    appendChat(chatDisplay, "\n🤖: " + chatbotResponse + "\n", Color.BLACK);

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

        // === Handle Enter key behavior depending on checkbox ===
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
                SwingUtilities.invokeLater(() -> callback.accept(response));
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to fetch chatbot response", e);
                SwingUtilities.invokeLater(() -> callback.accept("Sorry, an error occurred while fetching the response."));
            }
        }).start();
    }
}