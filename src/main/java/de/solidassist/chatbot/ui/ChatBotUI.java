package de.solidassist.chatbot.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public static void main(String[] args) {
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
        StyledDocument doc = chatDisplay.getStyledDocument();
        Style defaultStyle = chatDisplay.addStyle("default", null);
        StyleConstants.setFontFamily(defaultStyle, "SansSerif");
        StyleConstants.setFontSize(defaultStyle, 14);
        StyleConstants.setLineSpacing(defaultStyle, 0.3f); // More space between lines
        StyleConstants.setSpaceAbove(defaultStyle, 8);     // More space above each message
        StyleConstants.setSpaceBelow(defaultStyle, 8);     // More space below each message

        chatDisplay.setParagraphAttributes(defaultStyle, true);

        JScrollPane chatScroll = new JScrollPane(chatDisplay);
        topPanel.add(chatScroll, BorderLayout.CENTER);


        // Add the top panel to the center of the frame
        frame.add(topPanel, BorderLayout.CENTER);

        // === ROW 2: User input field and Send button ===
        JPanel middlePanel = new JPanel(new BorderLayout(5, 5));
        middlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JTextField inputField = new JTextField(); // User types here
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
                // Highlight user's message in blue
                appendChat(chatDisplay, "🙂: " + userText + "\n", Color.BLUE);
                inputField.setText("");
                // TODO: Replace this with real streamed response from the chatbot
                appendChat(chatDisplay, "🤖: typing...\n", Color.DARK_GRAY);
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
                    // Else allow default behavior (go to new line)
                }
            }
        });

        // === Settings Button Action Placeholder ===
        settingsButton.addActionListener(e -> {
            // TODO: Implement a settings window and open it here
            JOptionPane.showMessageDialog(frame, "Settings window will be added soon.");
        });

        // Show the window
        frame.setVisible(true);
    }

    /**
     * Logger instance used for reporting errors or important events.
     * Using java.util.logging to provide robust logging instead of printStackTrace().
     */
    private static final Logger logger = Logger.getLogger(ChatBotUI.class.getName());

    /**
     * Appends a new piece of styled text to the provided JTextPane.
     *
     * @param pane  The JTextPane where the message should be displayed.
     * @param text  The actual text content to append.
     * @param color The color in which the text should be rendered.
     *
     * This method adds styling (e.g., color) to the inserted text using Swing's style API.
     * Any exceptions during the insertion are logged using java.util.logging.
     */
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
}