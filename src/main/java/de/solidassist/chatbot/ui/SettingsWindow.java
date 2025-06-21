package de.solidassist.chatbot.ui;

import de.solidassist.chatbot.controller.ChatBotController;
import de.solidassist.chatbot.dao.ChatbotSettingsDAO;
import de.solidassist.chatbot.model.ChatbotSettings;
import de.solidassist.chatbot.util.AppPreferenceUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsWindow extends JFrame {
    private final Logger logger = Logger.getLogger(SettingsWindow.class.getName());
    private final ChatbotSettingsDAO settingsDAO = new ChatbotSettingsDAO();
    private List<ChatbotSettings> allSettings;
    private ChatbotSettings loadedSettings;

    private final JComboBox<String> profileSelector = new JComboBox<>();
    private final JTextField profileNameField = new JTextField(20);
    private final JTextField llmServerUrlField = new JTextField(20);
    private final JComboBox<String> llmProviderSelector = new JComboBox<>(new String[]{"ollama", "openai", "github"});
    private final JTextField llmModelField = new JTextField(20);
    private final JTextField modelAccessTokenField = new JTextField(20);
    private final JTextField referenceFileField = new JTextField(20);
    private final JSlider maxTokensSlider = new JSlider(0, 100, 70);
    private final JSlider temperatureSlider = new JSlider(0, 100, 50);
    private final JLabel maxTokensLabel = new JLabel("70%");
    private final JLabel temperatureLabel = new JLabel("50%");
    private final JButton newProfileButton = new JButton("New");
    private final JButton deleteProfileButton = new JButton("Delete");
    private final JButton browseButton = new JButton("Browse");
    private final JButton testButton = new JButton("Test");
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    /**
     * Constructor that initializes the Settings window and its components.
     */
    public SettingsWindow(JFrame frame) {
        setTitle("Chatbot Settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(500, 380);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Row 0: Profile selector with New and Delete buttons
        add(new JLabel("Select Profile:"), gbc);
        gbc.gridx++;
        add(profileSelector, gbc);
        gbc.gridx++;
        add(newProfileButton, gbc);
        gbc.gridx++;
        add(deleteProfileButton, gbc);

        // Row 1: Profile Name
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Profile Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        add(profileNameField, gbc);
        gbc.gridwidth = 1;

        // Row 2: LLM Server URL
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("LLM Server URL:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        add(llmServerUrlField, gbc);
        gbc.gridwidth = 1;

        // Row 3: LLM Provider
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("LLM Provider:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        add(llmProviderSelector, gbc);
        gbc.gridwidth = 1;

        // Row 4: Model Name
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("LLM Model Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        add(llmModelField, gbc);
        gbc.gridwidth = 1;

        // Row 5: Access Token
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Access Token:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        add(modelAccessTokenField, gbc);
        gbc.gridwidth = 1;

        // Row 6: Reference File with Browse button
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Reference File:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(referenceFileField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        add(browseButton, gbc);

        // Row 7: Max Tokens Slider
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Max Tokens:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(maxTokensSlider, gbc);
        gbc.gridx = 3;
        add(maxTokensLabel, gbc);
        gbc.gridwidth = 1;

        // Row 8: Temperature Slider
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Temperature:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(temperatureSlider, gbc);
        gbc.gridx = 3;
        add(temperatureLabel, gbc);
        gbc.gridwidth = 1;

        // Row 9: Buttons
        gbc.gridy++;
        gbc.gridx = 1;
        add(testButton, gbc);
        gbc.gridx++;
        add(saveButton, gbc);
        gbc.gridx++;
        add(cancelButton, gbc);

        setupListeners();
        loadProfileList();
    }

    /**
     * Loads available profile names into the combo box and loads the last selected one.
     */
    private void loadProfileList() {
        try {

            allSettings = settingsDAO.getAllSettings();
            profileSelector.removeAllItems();

            for (ChatbotSettings s : allSettings) {
                profileSelector.addItem(s.getProfileName());
            }

            // Try to restore the last selected profile by ID
            String lastSelectedIdStr = AppPreferenceUtils.loadPreference("lastSelectedProfileId");

            if (lastSelectedIdStr != null) {
                try {
                    int lastSelectedId = Integer.parseInt(lastSelectedIdStr);

                    for (int i = 0; i < allSettings.size(); i++) {
                        int currentId = allSettings.get(i).getId();

                        if (currentId == lastSelectedId) {
                            profileSelector.setSelectedIndex(i);
                            loadSelectedProfile();
                            return;
                        }
                    }

                } catch (NumberFormatException ex) {
                    logger.log(Level.WARNING, "Invalid profile ID in preferences: " + lastSelectedIdStr, ex);
                }
            }

            // Fallback: load first if nothing found
            if (!allSettings.isEmpty()) {
                System.out.println("Falling back to first profile (index 0)");
                profileSelector.setSelectedIndex(0);
                loadSelectedProfile();
            } else {
                System.out.println("No profiles available to load.");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to load profile list.", e);
        }
    }

    /**
     * Loads selected profile data into the form fields.
     */
    private void loadSelectedProfile() {
        int index = profileSelector.getSelectedIndex();
        if (index >= 0 && index < allSettings.size()) {
            loadedSettings = allSettings.get(index);
            profileNameField.setText(loadedSettings.getProfileName());
            llmServerUrlField.setText(loadedSettings.getLlmServerUrl());
            llmProviderSelector.setSelectedItem(loadedSettings.getLlmProvider());
            llmModelField.setText(loadedSettings.getLlmModelName());
            modelAccessTokenField.setText(loadedSettings.getModelAccessToken());
            referenceFileField.setText(loadedSettings.getReferenceFilePath());
            maxTokensSlider.setValue(loadedSettings.getMaxTokensPercent());
            temperatureSlider.setValue(loadedSettings.getTemperaturePercent());
        }
    }

    /**
     * Sets up listeners for sliders and buttons.
     */
    private void setupListeners() {
        profileSelector.addActionListener(e -> {
            int index = profileSelector.getSelectedIndex();
            if (index >= 0 && index < allSettings.size()) {
                loadedSettings = allSettings.get(index);
                loadSelectedProfile();

                // Save the ID of the selected profile as last used
                try {
                    AppPreferenceUtils.savePreference("lastSelectedProfileId", String.valueOf(loadedSettings.getId()));
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Failed to save last selected profile ID.", ex);
                }
            }
        });

        maxTokensSlider.addChangeListener(e -> maxTokensLabel.setText(maxTokensSlider.getValue() + "%"));
        temperatureSlider.addChangeListener(e -> temperatureLabel.setText(temperatureSlider.getValue() + "%"));

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                referenceFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Listener for the "New Profile" button: inserts a new profile based on current UI fields
        newProfileButton.addActionListener(e -> {
            try {
                // Read field values and create a new settings object
                ChatbotSettings newSettings = new ChatbotSettings();
                newSettings.setProfileName(profileNameField.getText().trim());
                newSettings.setLlmServerUrl(llmServerUrlField.getText().trim());
                newSettings.setLlmProvider(Objects.requireNonNull(llmProviderSelector.getSelectedItem()).toString());
                newSettings.setLlmModelName(llmModelField.getText().trim());
                newSettings.setModelAccessToken(modelAccessTokenField.getText().trim());
                newSettings.setReferenceFilePath(referenceFileField.getText().trim());
                newSettings.setMaxTokensPercent(maxTokensSlider.getValue());
                newSettings.setTemperaturePercent(temperatureSlider.getValue());

                // Insert the new settings into the database
                int newId = settingsDAO.insertSettings(newSettings);
                newSettings.setId(newId);
                loadedSettings = newSettings;

                // Add a new profile name to the combo box if not already present
                String profileName = newSettings.getProfileName();
                boolean alreadyExists = false;
                for (int i = 0; i < profileSelector.getItemCount(); i++) {
                    if (profileSelector.getItemAt(i).equals(profileName)) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (!alreadyExists) {
                    profileSelector.addItem(profileName);
                }
                profileSelector.setSelectedItem(profileName);

                // Inform the user
                JOptionPane.showMessageDialog(this,
                        "New profile \"" + profileName + "\" created successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to create new profile", ex);
                JOptionPane.showMessageDialog(this,
                        "Error creating profile: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteProfileButton.addActionListener(e -> {
            try {
                if (loadedSettings != null && settingsDAO.deleteSettings(loadedSettings.getId())) {
                    loadProfileList();
                }
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Failed to delete profile.", ex);
            }
        });

        saveButton.addActionListener(e -> {
            try {
                saveSettingsToDatabase(); // Try saving settings

                JOptionPane.showMessageDialog(this,
                        "Settings saved successfully. Please restart the application to apply the changes.",
                        "Settings Saved",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to save settings", ex);
                JOptionPane.showMessageDialog(this,
                        "An error occurred while saving settings:\n" + ex.getMessage(),
                        "Save Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Reads settings from UI fields and saves them to the database.
     * If a settings record exists, it will be updated; otherwise, a new record will be inserted.
     * After saving, the model settings are reloaded using ChatBotController.
     */
    private void saveSettingsToDatabase() {
        try {
            // Create a new settings object from current UI values
            ChatbotSettings settings = new ChatbotSettings();

            // Read values from UI fields
            settings.setProfileName(profileNameField.getText().trim());
            settings.setLlmServerUrl(llmServerUrlField.getText().trim());
            settings.setLlmProvider(Objects.requireNonNull(llmProviderSelector.getSelectedItem()).toString());
            settings.setLlmModelName(llmModelField.getText().trim());
            settings.setModelAccessToken(modelAccessTokenField.getText().trim());
            settings.setReferenceFilePath(referenceFileField.getText().trim());
            settings.setMaxTokensPercent(maxTokensSlider.getValue());
            settings.setTemperaturePercent(temperatureSlider.getValue());

            boolean success;

            // If a profile is already loaded, update it; otherwise insert a new one
            if (loadedSettings != null) {
                settings.setId(loadedSettings.getId());
                success = settingsDAO.updateSettings(settings);
                if (success) {
                    logger.info("Settings updated successfully.");
                } else {
                    logger.warning("Failed to update settings.");
                }
            } else {
                int newId = settingsDAO.insertSettings(settings);
                success = newId > 0;
                if (success) {
                    logger.info("New settings inserted with ID: " + newId);
                    settings.setId(newId);
                    loadedSettings = settings; // Update the reference to loaded settings
                } else {
                    logger.warning("Failed to insert new settings.");
                }
            }

            // Reload the settings globally
            if (success) {
                ChatBotController.setCurrentSettings(settings);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to save settings to database", ex);
        }
    }
}