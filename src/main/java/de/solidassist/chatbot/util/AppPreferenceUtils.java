package de.solidassist.chatbot.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for storing and retrieving key-value preferences
 * in a local configuration file inside the user's home directory.
 */
public class AppPreferenceUtils {

    private static final String CONFIG_DIR_NAME = ".solidassist";
    private static final String CONFIG_FILE_NAME = "preferences.txt";
    private static final Logger logger = Logger.getLogger(AppPreferenceUtils.class.getName());

    /**
     * Returns the full path to the preference file in the user's home directory.
     */
    private static Path getPreferencesFilePath() {
        String userHome = System.getProperty("user.home");
        Path configDir = Paths.get(userHome, CONFIG_DIR_NAME);

        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create config directory: " + configDir, e);
                throw new RuntimeException("Failed to create config directory: " + configDir, e);
            }
        }

        return configDir.resolve(CONFIG_FILE_NAME);
    }

    /**
     * Loads all stored preferences from the configuration file.
     *
     * @return a map of all key-value pairs
     */
    public static Map<String, String> loadAllPreferences() {
        Map<String, String> preferences = new HashMap<>();
        Path path = getPreferencesFilePath();

        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("=")) {
                        String[] parts = line.split("=", 2);
                        preferences.put(parts[0].trim(), parts[1].trim());
                    }
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Failed to read preferences from file: " + path, e);
            }
        }

        return preferences;
    }

    /**
     * Loads a single preference by key.
     *
     * @param key the preference key
     * @return the preference value, or null if not found
     */
    public static String loadPreference(String key) {
        return loadAllPreferences().get(key);
    }

    /**
     * Saves or updates a preference key-value pair.
     *
     * @param key the key to store
     * @param value the value to associate with the key
     */
    public static void savePreference(String key, String value) {
        Map<String, String> preferences = loadAllPreferences();
        preferences.put(key, value);
        saveAllPreferences(preferences);
        System.out.println("Saving preference: " + key + " = " + value);
    }

    /**
     * Saves all preferences to the configuration file.
     *
     * @param preferences the map of key-value pairs to store
     */
    private static void saveAllPreferences(Map<String, String> preferences) {
        Path path = getPreferencesFilePath();

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Map.Entry<String, String> entry : preferences.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write preferences to file: " + path, e);
        }
    }
}