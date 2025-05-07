package de.solidassist.chatbot.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * SettingsWindow is a modal dialog that allows users to configure various settings
 * for the chatbot application. These include:
 *
 * - LLM API URL: The endpoint for the language model service.
 * - Server Selector: Allows choosing between local and remote server.
 * - Model Selector: Selects the model type (e.g., llama2, mistral).
 * - Reference Document: Lets user browse and load a text or PDF file used as knowledge input for RAG.
 * - Max Tokens: A slider that sets max tokens as a percentage, to adapt to different model limits.
 * - Temperature: A slider representing the randomness of output, also as a percentage.
 * - Language: Sets the interface or request language (e.g., English or German).
 * - Apply / Save / Cancel buttons: Apply updates, persist settings, or close the window without saving.
 *
 * The class uses GridBagLayout for flexible alignment and responsive row structure.
 * Helper method `addRow` is used to reduce code duplication when adding simple label-component pairs.
 *
 * TODOs:
 * - Implement logic for applying changes in real-time.
 * - Map slider percentages to actual token/temperature values based on model.
 * - Implement saving and loading of settings using a configuration manager or file.
 *
 * Usage:
 * Create an instance of this dialog and call setVisible(true) to show it:
 *     new SettingsWindow(parentFrame).setVisible(true);
 */
public class SettingsWindow extends JDialog {

    private final JTextField propertiesFileField = new JTextField(20);

    public SettingsWindow(JFrame parent) {
        super(parent, "Settings", true);
        setSize(550, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // === Row 1: LLM API URL ===
        JTextField apiUrlField = new JTextField(20);
        addRow(row++, "LLM API URL:", apiUrlField);

        // === Row 2: Server Selector ===
        JComboBox<String> serverSelector = new JComboBox<>(new String[]{"Localhost", "Remote"});
        addRow(row++, "Server Selector:", serverSelector);

        // === Row 3: Model Selector ===
        JComboBox<String> modelSelector = new JComboBox<>(new String[]{"llama2", "mistral", "gpt4all"});
        addRow(row++, "Model Selector:", modelSelector);

        // === Row 4: Reference Document + Browse Button ===
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Reference Document:"), gbc);

        gbc.gridx = 1;
        add(propertiesFileField, gbc);

        JButton browseButton = new JButton("Browse...");
        gbc.gridx = 2;
        add(browseButton, gbc);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(SettingsWindow.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                propertiesFileField.setText(selectedFile.getAbsolutePath());
            }
        });

        row++;

        // === Row 5: Max Tokens Slider (as percentage) ===
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Max Tokens (%):"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        // Represent Max Tokens as percentage (0-100%)
        JSlider maxTokensSlider = new JSlider(0, 100, 25);  // Default 25%
        maxTokensSlider.setMajorTickSpacing(25);
        maxTokensSlider.setPaintTicks(true);
        maxTokensSlider.setPaintLabels(true);
        add(maxTokensSlider, gbc);
        gbc.gridwidth = 1;
        row++;

        // TODO: Map this percentage to actual max tokens based on selected model
        // Example: int actualMaxTokens = (int)(maxTokensSlider.getValue() / 100.0 * getMaxTokenForModel(modelName));

        // === Row 6: Temperature Slider (as percentage) ===
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Temperature (%):"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        // Represent Temperature as percentage (0-100%)
        JSlider temperatureSlider = new JSlider(0, 100, 70); // Default 70%
        temperatureSlider.setMajorTickSpacing(20);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        add(temperatureSlider, gbc);
        gbc.gridwidth = 1;
        row++;

        // TODO: Convert this to temperature value before sending to model
        // Example: double temperature = temperatureSlider.getValue() / 100.0;

        // === Row 7: Language ===
        JComboBox<String> languageSelector = new JComboBox<>(new String[]{"English", "German"});
        addRow(row++, "Language:", languageSelector);

        // === Row 8: Buttons (Apply, Save, Cancel) ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyButton = new JButton("Apply");
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(applyButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        // === Button Actions ===
        applyButton.addActionListener(e -> {
            // TODO: Apply changes to current session without closing the window
            System.out.println("Apply clicked");
        });

        saveButton.addActionListener(e -> {
            // TODO: Save all settings to file or config manager and close
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Utility method to add a label and component to a row.
     */
    private void addRow(int row, String labelText, JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(component, gbc);
    }
}