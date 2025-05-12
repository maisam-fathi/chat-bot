-- Create the main database
CREATE DATABASE IF NOT EXISTS solidassist_chatcore
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE solidassist_chatcore;

-- Table: chat_sessions
CREATE TABLE IF NOT EXISTS chat_sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- Table: chat_messages
CREATE TABLE IF NOT EXISTS chat_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
    sender ENUM('user', 'bot') NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES chat_sessions(id) ON DELETE CASCADE
    );

-- Table: chatbot_settings
CREATE TABLE IF NOT EXISTS chatbot_settings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    llm_server_url VARCHAR(500) NOT NULL,
    llm_provider VARCHAR(100) NOT NULL,
    llm_model_name VARCHAR(100) NOT NULL,
    max_tokens_percent INT NOT NULL,
    temperature_percent INT NOT NULL,
    language VARCHAR(50) NOT NULL,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
