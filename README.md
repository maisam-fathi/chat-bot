# SolidAssist Chatbot

<img src="https://github.com/maisam-fathi/chat-bot/blob/master/assets/icon.png" width="125">

SolidAssist Chatbot is a powerful and user-friendly desktop application that allows users to interact with both offline and online large language models (LLMs). It supports models from Ollama, OpenAI, and GitHub Copilot, offering a flexible and customizable chat experience with full session history and profile-based configuration.

---

## Features

- Multi-LLM Support: Seamlessly switch between offline Ollama models and online providers like OpenAI and GitHub Copilot using access tokens.
- Profile Management: Create multiple profiles with custom settings (temperature, token limit, etc.) for different LLMs.
- Chat Sessions: All conversations are stored locally in an offline SQLite database, organized by session.
- UI: Built with Java Swing, featuring a clean interface with chat history, session controls, and settings panel.
- Offline-First: Fully functional without internet when using downloaded Ollama models.
- Local Storage: All settings and chat data are stored securely on your device.

---

## Getting Started

You can download the latest setup file from the official website:

[www.solidassist.de](https://solidassist.de/solidassist-ai-chatbot/)

---

## Current Status

This is a SNAPSHOT release intended for testing and feedback:

**Version:** Chatbot-0.0.1-SNAPSHOT  
**Stability:** Not yet production-ready

---

## Technologies Used

- Java 17+
- Swing for desktop UI
- LangChain4j for LLM integration:
  - langchain4j-ollama
  - langchain4j-open-ai
  - langchain4j-github-models
- SQLite via JDBC
- Jackson and OkHttp for JSON and HTTP
- SLF4J for logging

---

## Usage Flow

1. Create a Profile: Define a new LLM profile with model name, provider, and access token (if needed).
2. Download Models: For offline use, download Ollama models via command line.
3. Start Chatting: Launch the app and begin chatting. A new session is created automatically.
4. Manage Sessions: Use the left panel to view, select, or delete chat sessions.
5. Switch Models: Close and reopen the app after changing profiles to apply new settings.

---

## Screenshots

- Chat UI
<img src="https://github.com/maisam-fathi/chat-bot/blob/master/assets/ChatBotUI-Screenshot.jpg" width="886" alt="Screenshot of the interface">
  
- Profile Settings Window
<img src="https://github.com/maisam-fathi/chat-bot/blob/master/assets/SettingsWindow-Screenshot.jpg" width="486" alt="Screenshot of the Profile Settings Window">

---

## Roadmap

- Display chatbot responses dynamically in the UI
- Add prompt template or system prompt
- Entity memory: parse user input for names, places, etc.
- Generate embeddings for messages
- Summary memory: implement chat summarizer
- Download models directly from the UI
- Live model switching without restart
- Linux and macOS support

---

## Contributing

Contributions are welcome. If you're a developer and want to help improve this project:

1. Fork the repository
2. Create a new branch
3. Commit your changes
4. Submit a pull request

---

## Feedback & Support

We welcome your feedback.  
Please send bug reports, feature requests, or suggestions to:

**Email:** maisam.fathi@gmail.com

---

## License
This project is licensed under the GNU Affero General Public License v3.0.  
[See the LICENSE file for details](https://github.com/maisam-fathi/chat-bot/blob/master/LICENSE)

---

## Changelog

### Chatbot-0.0.1-SNAPSHOT
- Initial test release
- Core features implemented: offline/online LLM integration, chat session management, profile-based model configuration
