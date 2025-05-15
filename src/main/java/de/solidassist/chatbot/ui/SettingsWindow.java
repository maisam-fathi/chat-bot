package de.solidassist.chatbot.ui;

import javax.swing.*;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.awt.*;
import java.io.File;
import de.solidassist.chatbot.dao.ChatbotSettingsDAO;
import de.solidassist.chatbot.model.ChatbotSettings;

/**
 * SettingsWindow is a modal dialog that allows users to configure various settings
 * for the chatbot application. These include:
 *
 * - LLM Server URL: The endpoint for the language model service.
 * - Provider: Allows choosing between local and remote server.
 * - Model Name: Selects the model type (e.g., llama2, mistral).
 * - Reference Document: Lets user browse and load a text or PDF file used as knowledge input for RAG.
 * - Max Tokens: A slider that sets max tokens as a percentage, to adapt to different model limits.
 * - Temperature: A slider representing the randomness of output, also as a percentage.
 * - Language: Sets the interface or request language (e.g., English or German).
 * - Apply / Save / Cancel buttons:
 *     - Apply: Save the settings to the database and notify user that a restart is required.
 *     - Save: Same as Apply, but also closes the dialog.
 *     - Cancel: Closes the window without saving.
 *
 * Behavior:
 * - Settings are persisted in the database using ChatbotSettingsDAO.
 * - Changes are not applied at runtime; the user is notified to restart the application after saving.
 * - The class uses GridBagLayout for flexible alignment and responsive row structure.
 * - Helper method `addRow` is used to reduce code duplication when adding simple label-component pairs.
 *
 * TODOs:
 * - Implement runtime refreshing of LLM settings in future versions.
 * - Implement advanced mapping of slider percentages to actual model-specific token/temperature values.
 * - Consider replacing database storage with a more dynamic configuration manager supporting live reload.
 *
 * Usage:
 * Create an instance of this dialog and call setVisible(true) to show it:
 *     new SettingsWindow(parentFrame).setVisible(true);
 */

public class SettingsWindow extends JDialog {

    private final JTextField propertiesFileField = new JTextField(20);
    private final ChatbotSettingsDAO settingsDAO = new ChatbotSettingsDAO();
    private ChatbotSettings loadedSettings;
    private static final Logger logger = Logger.getLogger(SettingsWindow.class.getName());
    private final JTextField llmServerUrlField;
    private final JComboBox<String> llmProviderSelector;
    private final JComboBox<String> llmModelSelector;
    private final JSlider maxTokensSlider;
    private final JSlider temperatureSlider;
    private final JComboBox<String> languageSelector;

    public SettingsWindow(JFrame parent) {
        super(parent, "Settings", true);
        setSize(550, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // === Row 1: LLM Server URL ===
        llmServerUrlField = new JTextField(20);
        llmServerUrlField.setText("http://localhost:11434"); // Default URL for Ollama (Local)
        addRow(row++, "LLM Server URL:", llmServerUrlField);

        // === Row 2: Provider ===
        llmProviderSelector = new JComboBox<>(new String[]{
                "Localhost", "Remote", "OpenAI", "HuggingFace", "Ollama"
        }); // Added more provider options
        addRow(row++, "Provider:", llmProviderSelector);

        // === Row 3: Model Name ===
        llmModelSelector = new JComboBox<>(new String[]{
                "llama2", "llama3", "mistral", "gpt-3.5", "gpt-4", "bert", "gpt4all"
        }); // Expanded model options
        addRow(row++, "Model Name:", llmModelSelector);

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
        maxTokensSlider = new JSlider(0, 100, 25);  // Default 25%
        maxTokensSlider.setMajorTickSpacing(25);
        maxTokensSlider.setPaintTicks(true);
        maxTokensSlider.setPaintLabels(true);
        add(maxTokensSlider, gbc);
        gbc.gridwidth = 1;
        row++;

        // === Row 6: Temperature Slider (as percentage) ===
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Temperature (%):"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        // Represent Temperature as percentage (0-100%)
        temperatureSlider = new JSlider(0, 100, 70); // Default 70%
        temperatureSlider.setMajorTickSpacing(20);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        add(temperatureSlider, gbc);
        gbc.gridwidth = 1;
        row++;

        // === Row 7: Language ===
        languageSelector = new JComboBox<>(new String[]{"English", "German"});
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

        // For simplicity, load the first setting if exists (assuming only one config is active)
        try {
            if (!settingsDAO.getAllSettings().isEmpty()) {
                loadedSettings = settingsDAO.getAllSettings().get(0);
                llmServerUrlField.setText(loadedSettings.getLlmServerUrl());
                llmProviderSelector.setSelectedItem(loadedSettings.getLlmProvider());
                llmModelSelector.setSelectedItem(loadedSettings.getLlmModelName());
                maxTokensSlider.setValue(loadedSettings.getMaxTokensPercent());
                temperatureSlider.setValue(loadedSettings.getTemperaturePercent());
                languageSelector.setSelectedItem(loadedSettings.getLanguage());
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to load settings from database", ex);
        }

        // === Button Actions ===
        applyButton.addActionListener(e -> {
            saveSettingsToDatabase();
            JOptionPane.showMessageDialog(this,
                    "Settings saved. Please restart the application to apply the changes.",
                    "Restart Required",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        saveButton.addActionListener(e -> {
            saveSettingsToDatabase();
            JOptionPane.showMessageDialog(this,
                    "Settings saved. Please restart the application to apply the changes.",
                    "Restart Required",
                    JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * Reads settings from UI fields and saves them to the database.
     * If a settings record exists, it will be updated; otherwise, a new record will be inserted.
     * After saving, the model settings are reloaded using ChatBotController.
     */
    private void saveSettingsToDatabase() {
        try {
            ChatbotSettings settings = new ChatbotSettings();

            // Read fields from UI
            settings.setLlmServerUrl(llmServerUrlField.getText().trim());
            settings.setLlmProvider(Objects.requireNonNull(llmProviderSelector.getSelectedItem()).toString());
            settings.setLlmModelName(Objects.requireNonNull(llmModelSelector.getSelectedItem()).toString());
            settings.setMaxTokensPercent(maxTokensSlider.getValue());
            settings.setTemperaturePercent(temperatureSlider.getValue());
            settings.setLanguage(Objects.requireNonNull(languageSelector.getSelectedItem()).toString());

            // Check if there is an existing record and update or insert accordingly
            boolean success;
            if (loadedSettings != null) {
                settings.setId(loadedSettings.getId()); // Ensure the correct ID for update
                success = settingsDAO.updateSettings(settings);
                if (success) {
                    logger.info("Settings updated successfully.");
                } else {
                    logger.warning("Failed to update settings.");
                }
            } else {
                int newId = settingsDAO.insertSettings(settings);
                logger.info("New settings inserted with ID: " + newId);
                success = (newId > 0);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to save settings to database", ex);
        }
    }
}