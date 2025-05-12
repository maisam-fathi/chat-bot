-- Insert 5 test chat sessions into the chat_sessions table
INSERT INTO chat_sessions (session_name, created_at)
VALUES
  ('Session 1', NOW()),
  ('Session 2', NOW()),
  ('Session 3', NOW()),
  ('Session 4', NOW()),
  ('Session 5', NOW());

-- Insert 20 test messages for the chat sessions
INSERT INTO chat_messages (session_id, sender, message, created_at)
VALUES
  -- Messages for session 1
  (1, 'user', 'Hello, how are you?', NOW()),
  (1, 'bot', 'I am fine, thank you! How can I help you?', NOW()),
  (1, 'user', 'Can you tell me the weather?', NOW()),
  (1, 'bot', 'Sure, I can! Where are you located?', NOW()),
  (1, 'user', 'I am in New York.', NOW()),

  -- Messages for session 2
  (2, 'user', 'What is the time?', NOW()),
  (2, 'bot', 'It is 3 PM now.', NOW()),
  (2, 'user', 'Thanks!', NOW()),
  (2, 'bot', 'You are welcome!', NOW()),

  -- Messages for session 3
  (3, 'user', 'Tell me a joke.', NOW()),
  (3, 'bot', 'Why don’t skeletons fight each other? They don’t have the guts!', NOW()),
  (3, 'user', 'Haha, that was funny!', NOW()),

  -- Messages for session 4
  (4, 'user', 'How can I reset my password?', NOW()),
  (4, 'bot', 'You can reset your password by clicking on "Forgot Password" on the login screen.', NOW()),
  (4, 'user', 'Great, I will try that.', NOW()),

  -- Messages for session 5
  (5, 'user', 'Where can I find the documentation?', NOW()),
  (5, 'bot', 'You can find it at the top of the page under "Docs".', NOW());
