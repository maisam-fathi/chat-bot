package de.solidassist.chatbot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage a singleton database connection to the SolidAssist ChatCore MySQL database.
 * <p>
 * This class ensures that only one instance of the database connection exists during the application's lifecycle.
 * It handles connection initialization, access, and closure.
 * </p>
 *
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * DatabaseConnection dbConnection = DatabaseConnection.getInstance();
 * Connection connection = dbConnection.getConnection();
 * }
 * </pre>
 * </p>
 *
 * <p>
 * Note: The connection should be explicitly closed by calling {@link #closeConnection()} when no longer needed.
 * </p>
 */
public class DatabaseConnection {

    /**
     * The singleton instance of {@code DatabaseConnection}.
     */
    private static DatabaseConnection instance;

    /**
     * The JDBC connection object.
     */
    private final Connection connection;

    /**
     * Database URL.
     */
    private final String url = "jdbc:mysql://localhost:3306/solidassist_chatcore";

    /**
     * Database username.
     */
    private final String username = "root";

    /**
     * Database password.
     */
    private final String password = "3556";

    /**
     * Private constructor that initializes the database connection.
     *
     * @throws SQLException if the JDBC driver is not found or the connection cannot be established.
     */
    private DatabaseConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Initialize the connection
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC Driver not found.", ex);
        }
    }

    /**
     * Returns the singleton instance of {@code DatabaseConnection}.
     * If the instance is not initialized or the existing connection is closed,
     * a new instance is created.
     *
     * @return the singleton {@code DatabaseConnection} instance.
     * @throws SQLException if an error occurs while initializing the connection.
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Returns the active database connection.
     *
     * @return the {@code Connection} object.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection explicitly.
     * If the connection is already closed or null, this method does nothing.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error closing the database connection: " + ex.getMessage());
        }
    }
}
