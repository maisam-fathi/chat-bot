package de.solidassist.chatbot.util;

import java.io.IOException;
import java.nio.file.*;

/**
 * Utility class for determining the path to the SQLite database file
 * in a cross-platform compatible location inside the user's home directory.
 */
public class DatabasePathUtils {

    private static final String CONFIG_DIR_NAME = ".solidassist";
    private static final String DATABASE_FILE_NAME = "solidassist_chatcore.db";

    /**
     * Returns the full path to the SQLite database file.
     * If the directory does not exist, it will be created.
     *
     * @return Path to the database file
     */
    public static Path getDatabaseFilePath() {
        String userHome = System.getProperty("user.home");
        Path configDir = Paths.get(userHome, CONFIG_DIR_NAME);
        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create config directory: " + configDir, e);
            }
        }
        return configDir.resolve(DATABASE_FILE_NAME);
    }
}