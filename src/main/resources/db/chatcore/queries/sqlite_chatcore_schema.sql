-- Table: chat_sessions
CREATE TABLE IF NOT EXISTS chat_sessions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    session_name TEXT NOT NULL,
    created_at DATETIME DEFAULT (datetime('now'))
);

-- Table: chat_messages
CREATE TABLE IF NOT EXISTS chat_messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    session_id INTEGER NOT NULL,
    sender TEXT CHECK(sender IN ('user', 'bot')) NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME DEFAULT (datetime('now')),
    FOREIGN KEY (session_id) REFERENCES chat_sessions(id) ON DELETE CASCADE
);

-- Table: chatbot_settings
CREATE TABLE IF NOT EXISTS chatbot_settings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    llm_server_url TEXT NOT NULL,
    llm_provider TEXT NOT NULL,
    llm_model_name TEXT NOT NULL,
    max_tokens_percent INTEGER NOT NULL,
    temperature_percent INTEGER NOT NULL,
    language TEXT NOT NULL,
    updated_at DATETIME DEFAULT (datetime('now'))
);