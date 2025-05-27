-- Completely remove all records from the 'chat_messages' table and reset the AUTO_INCREMENT counter.
TRUNCATE TABLE solidassist_chatcore.chat_messages;

-- Temporarily disable safe update mode to allow deletion without strict WHERE clause.
SET SQL_SAFE_UPDATES = 0;

-- Delete all records from the 'chat_sessions' table and reset the AUTO_INCREMENT counter to 1.
DELETE FROM solidassist_chatcore.chat_sessions;
ALTER TABLE solidassist_chatcore.chat_sessions AUTO_INCREMENT = 1;

-- Re-enable safe update mode to restore safety settings.
SET SQL_SAFE_UPDATES = 1;


--  Add 43 sessions to chat_sessions table
INSERT INTO chat_sessions (id, session_name, created_at) VALUES
(1, 'Travel Tips', '2023-01-02 12:00:00'),
(2, 'Health Advice', '2023-01-03 12:00:00'),
(3, 'Programming Help', '2023-01-04 12:00:00'),
(4, 'Science Facts', '2023-01-05 12:00:00'),
(5, 'Movie Reviews', '2023-01-06 12:00:00'),
(6, 'Tech News', '2023-01-07 12:00:00'),
(7, 'Fitness Plans', '2023-01-08 12:00:00'),
(8, 'Cooking Recipes', '2023-01-09 12:00:00'),
(9, 'History Lessons', '2023-01-10 12:00:00'),
(10, 'Language Learning', '2023-01-11 12:00:00'),
(11, 'Finance Tips', '2023-01-12 12:00:00'),
(12, 'Pet Care', '2023-01-13 12:00:00'),
(13, 'Car Maintenance', '2023-01-14 12:00:00'),
(14, 'Home Decor', '2023-01-15 12:00:00'),
(15, 'Study Hacks', '2023-01-16 12:00:00'),
(16, 'Gaming Trends', '2023-01-17 12:00:00'),
(17, 'Book Summaries', '2023-01-18 12:00:00'),
(18, 'Art Techniques', '2023-01-19 12:00:00'),
(19, 'Job Interview', '2023-01-20 12:00:00'),
(20, 'Music Theory', '2023-01-21 12:00:00'),
(21, 'Weather Update', '2023-01-22 12:00:00'),
(22, 'Relationship Talk', '2023-01-23 12:00:00'),
(23, 'Mental Health', '2023-01-24 12:00:00'),
(24, 'Gardening Tips', '2023-01-25 12:00:00'),
(25, 'Fashion Trends', '2023-01-26 12:00:00'),
(26, 'Yoga Practice', '2023-01-27 12:00:00'),
(27, 'Photography Guide', '2023-01-28 12:00:00'),
(28, 'Legal Questions', '2023-01-29 12:00:00'),
(29, 'Marketing Strategies', '2023-01-30 12:00:00'),
(30, 'AI Insights', '2023-01-31 12:00:00'),
(31, 'Homework Help', '2023-02-01 12:00:00'),
(32, 'Diet Plans', '2023-02-02 12:00:00'),
(33, 'Travel Budget', '2023-02-03 12:00:00'),
(34, 'Insurance Guide', '2023-02-04 12:00:00'),
(35, 'Medical Questions', '2023-02-05 12:00:00'),
(36, 'Fitness Routine', '2023-02-06 12:00:00'),
(37, 'Writing Tips', '2023-02-07 12:00:00'),
(38, 'Startup Ideas', '2023-02-08 12:00:00'),
(39, 'Parenting Advice', '2023-02-09 12:00:00'),
(40, 'Home Security', '2023-02-10 12:00:00'),
(41, 'Cybersecurity Basics', '2023-02-11 12:00:00'),
(42, 'CV Writing', '2023-02-12 12:00:00'),
(43, 'Cooking Tips', '2023-02-13 12:00:00');


--  Add 338 massege to chat_messages table
INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 1: Travel Tips
(1, 'user', 'What are the best ways to prepare for an international trip?', '2023-01-02 12:00:00'),
(1, 'bot', 'Make sure your passport is valid for at least six months, check visa requirements, and buy travel insurance.', '2023-01-02 12:01:00'),
(1, 'user', 'How can I avoid jet lag?', '2023-01-02 12:02:00'),
(1, 'bot', 'Adjust your sleep schedule before traveling, stay hydrated during the flight, and get sunlight exposure after arrival.', '2023-01-02 12:03:00'),
(1, 'user', 'Is it safe to use public Wi-Fi abroad?', '2023-01-02 12:04:00'),
(1, 'bot', 'Public Wi-Fi can be risky. Use a VPN to protect your data and avoid accessing sensitive accounts.', '2023-01-02 12:05:00'),
(1, 'user', 'What should I pack for a week-long trip?', '2023-01-02 12:06:00'),
(1, 'bot', 'Pack versatile clothing, chargers, medications, travel documents, and a first aid kit for emergencies.', '2023-01-02 12:07:00'),
(1, 'user', 'Any tips for saving money while traveling?', '2023-01-02 12:08:00'),
(1, 'bot', 'Book flights early, use public transportation, eat local food, and consider city tourist passes.', '2023-01-02 12:09:00'),

-- Session 2: Health Advice
(2, 'user', 'How can I boost my immune system naturally?', '2023-01-03 12:00:00'),
(2, 'bot', 'Eat a balanced diet rich in fruits and vegetables, get regular exercise, and ensure adequate sleep.', '2023-01-03 12:01:00'),
(2, 'user', 'Is vitamin C effective for colds?', '2023-01-03 12:02:00'),
(2, 'bot', 'Vitamin C may reduce cold duration but isn’t proven to prevent them entirely.', '2023-01-03 12:03:00'),
(2, 'user', 'How much water should I drink daily?', '2023-01-03 12:04:00'),
(2, 'bot', 'Aim for about 8 cups or 2 liters daily, more if you exercise or are in a hot climate.', '2023-01-03 12:05:00'),
(2, 'user', 'What’s the best way to lower blood pressure?', '2023-01-03 12:06:00'),
(2, 'bot', 'Reduce salt intake, manage stress, exercise regularly, and follow your doctor’s advice.', '2023-01-03 12:07:00'),
(2, 'user', 'Can I exercise if I have a cold?', '2023-01-03 12:08:00'),
(2, 'bot', 'Light exercise is okay if symptoms are mild, but rest is better if you feel very sick.', '2023-01-03 12:09:00'),

-- Session 3: Programming Help
(3, 'user', 'How do I fix a syntax error in Python?', '2023-01-04 12:00:00'),
(3, 'bot', 'Check for missing colons, indentation issues, or unmatched parentheses in your code.', '2023-01-04 12:01:00'),
(3, 'user', 'What is a variable?', '2023-01-04 12:02:00'),
(3, 'bot', 'A variable stores data values and can be used to reference information in your program.', '2023-01-04 12:03:00'),
(3, 'user', 'How do I write a loop?', '2023-01-04 12:04:00'),
(3, 'bot', 'Use "for" or "while" loops to repeat code blocks; for example, "for i in range(5):".', '2023-01-04 12:05:00'),
(3, 'user', 'What is a function?', '2023-01-04 12:06:00'),
(3, 'bot', 'A function is a reusable block of code that performs a specific task when called.', '2023-01-04 12:07:00'),
(3, 'user', 'How do I debug my code?', '2023-01-04 12:08:00'),
(3, 'bot', 'Use print statements or debugging tools in your IDE to trace and fix issues.', '2023-01-04 12:09:00'),

-- Session 4: Science Facts
(4, 'user', 'What is the speed of light?', '2023-01-05 12:00:00'),
(4, 'bot', 'The speed of light in a vacuum is about 299,792 kilometers per second.', '2023-01-05 12:01:00'),
(4, 'user', 'Why do stars twinkle?', '2023-01-05 12:02:00'),
(4, 'bot', 'Stars twinkle due to the Earth’s atmosphere bending their light as it passes through.', '2023-01-05 12:03:00'),
(4, 'user', 'What causes rainbows?', '2023-01-05 12:04:00'),
(4, 'bot', 'Rainbows form when sunlight refracts, reflects, and disperses through raindrops.', '2023-01-05 12:05:00'),
(4, 'user', 'What is gravity?', '2023-01-05 12:06:00'),
(4, 'bot', 'Gravity is a force that attracts two bodies towards each other, like Earth pulling objects down.', '2023-01-05 12:07:00'),
(4, 'user', 'How old is the Earth?', '2023-01-05 12:08:00'),
(4, 'bot', 'The Earth is about 4.5 billion years old based on geological evidence.', '2023-01-05 12:09:00'),

-- Session 5: Movie Reviews
(5, 'user', 'What is a good sci-fi movie to watch?', '2023-01-06 12:00:00'),
(5, 'bot', 'I recommend "Blade Runner 2049" for its stunning visuals and deep story.', '2023-01-06 12:01:00'),
(5, 'user', 'Who directed "Inception"?', '2023-01-06 12:02:00'),
(5, 'bot', '"Inception" was directed by Christopher Nolan, known for complex narratives.', '2023-01-06 12:03:00'),
(5, 'user', 'What makes a movie critically acclaimed?', '2023-01-06 12:04:00'),
(5, 'bot', 'Strong storytelling, good acting, cinematography, and emotional impact contribute.', '2023-01-06 12:05:00'),
(5, 'user', 'Any recent good releases?', '2023-01-06 12:06:00'),
(5, 'bot', 'Recent hits include "Dune" and "The Batman," praised for visuals and performances.', '2023-01-06 12:07:00'),
(5, 'user', 'Should I watch movies in theaters or at home?', '2023-01-06 12:08:00'),
(5, 'bot', 'Theaters offer immersive experiences, but home viewing is more convenient and flexible.', '2023-01-06 12:09:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 6: Tech News
(6, 'user', 'What are the biggest tech headlines this week?', '2023-01-07 12:00:00'),
(6, 'bot', 'Apple just announced a new line of AI-powered devices, and Google launched a major update to its search algorithm.', '2023-01-07 12:01:00'),
(6, 'user', 'Is there any news about electric cars?', '2023-01-07 12:02:00'),
(6, 'bot', 'Yes, Tesla unveiled a new battery technology promising higher range and faster charging times.', '2023-01-07 12:03:00'),
(6, 'user', 'How is AI evolving lately?', '2023-01-07 12:04:00'),
(6, 'bot', 'AI is being used in everything from healthcare diagnostics to content generation and autonomous vehicles.', '2023-01-07 12:05:00'),
(6, 'user', 'What’s the latest in cybersecurity?', '2023-01-07 12:06:00'),
(6, 'bot', 'Recent reports show a rise in phishing attacks, pushing companies to adopt zero-trust security models.', '2023-01-07 12:07:00'),
(6, 'user', 'Any exciting new gadgets?', '2023-01-07 12:08:00'),
(6, 'bot', 'Wearables with health tracking, smart glasses, and foldable phones are gaining popularity this year.', '2023-01-07 12:09:00'),

-- Session 7: Fitness Plans
(7, 'user', 'Can you suggest a basic workout routine?', '2023-01-08 12:00:00'),
(7, 'bot', 'Start with bodyweight exercises like squats, push-ups, planks, and lunges, doing three sets of each.', '2023-01-08 12:01:00'),
(7, 'user', 'How many days a week should I work out?', '2023-01-08 12:02:00'),
(7, 'bot', 'Aim for at least three to five days a week, balancing cardio, strength, and rest days.', '2023-01-08 12:03:00'),
(7, 'user', 'What’s the best time to exercise?', '2023-01-08 12:04:00'),
(7, 'bot', 'The best time is whenever you can stick to consistently; morning workouts may boost metabolism.', '2023-01-08 12:05:00'),
(7, 'user', 'Should I stretch before or after a workout?', '2023-01-08 12:06:00'),
(7, 'bot', 'Do dynamic stretches before workouts and static stretching after to improve flexibility and recovery.', '2023-01-08 12:07:00'),
(7, 'user', 'How can I track my progress?', '2023-01-08 12:08:00'),
(7, 'bot', 'Use a fitness app or journal to record workouts, set goals, and measure strength or endurance gains.', '2023-01-08 12:09:00'),

-- Session 8: Cooking Recipes
(8, 'user', 'Can you share a simple pasta recipe?', '2023-01-09 12:00:00'),
(8, 'bot', 'Cook pasta, sauté garlic in olive oil, add chopped tomatoes, salt, and basil, then mix with the pasta.', '2023-01-09 12:01:00'),
(8, 'user', 'What’s a quick breakfast idea?', '2023-01-09 12:02:00'),
(8, 'bot', 'Try scrambled eggs with spinach and toast or overnight oats with fruit and yogurt.', '2023-01-09 12:03:00'),
(8, 'user', 'How do I cook fluffy rice?', '2023-01-09 12:04:00'),
(8, 'bot', 'Rinse rice, use the right water ratio, bring to a boil, simmer covered, then let it rest before serving.', '2023-01-09 12:05:00'),
(8, 'user', 'Can you suggest a vegetarian dinner?', '2023-01-09 12:06:00'),
(8, 'bot', 'Grilled vegetables with quinoa and hummus or a chickpea curry with brown rice are great options.', '2023-01-09 12:07:00'),
(8, 'user', 'Any dessert I can make fast?', '2023-01-09 12:08:00'),
(8, 'bot', 'Mix crushed cookies with cream cheese and dip in melted chocolate for quick truffles.', '2023-01-09 12:09:00'),

-- Session 9: History Lessons
(9, 'user', 'When did World War II begin?', '2023-01-10 12:00:00'),
(9, 'bot', 'World War II started on September 1, 1939, when Germany invaded Poland.', '2023-01-10 12:01:00'),
(9, 'user', 'Who was Julius Caesar?', '2023-01-10 12:02:00'),
(9, 'bot', 'Julius Caesar was a Roman general and statesman who played a key role in ending the Roman Republic.', '2023-01-10 12:03:00'),
(9, 'user', 'What was the Renaissance?', '2023-01-10 12:04:00'),
(9, 'bot', 'The Renaissance was a cultural movement from the 14th to 17th century emphasizing art, science, and learning.', '2023-01-10 12:05:00'),
(9, 'user', 'Who discovered America?', '2023-01-10 12:06:00'),
(9, 'bot', 'Christopher Columbus is credited with discovering America in 1492, though native peoples lived there long before.', '2023-01-10 12:07:00'),
(9, 'user', 'What caused the fall of the Roman Empire?', '2023-01-10 12:08:00'),
(9, 'bot', 'Multiple factors led to Rome’s fall, including invasions, economic troubles, and internal decay.', '2023-01-10 12:09:00'),

-- Session 10: Language Learning
(10, 'user', 'What’s the best way to learn a new language?', '2023-01-11 12:00:00'),
(10, 'bot', 'Practice daily, use flashcards, speak with native speakers, and immerse yourself in the language.', '2023-01-11 12:01:00'),
(10, 'user', 'How long does it take to become fluent?', '2023-01-11 12:02:00'),
(10, 'bot', 'It depends on the language and effort, but consistent learners may see fluency in 6–18 months.', '2023-01-11 12:03:00'),
(10, 'user', 'Are language apps effective?', '2023-01-11 12:04:00'),
(10, 'bot', 'Yes, apps like Duolingo and Babbel can help build vocabulary and grammar skills.', '2023-01-11 12:05:00'),
(10, 'user', 'How can I improve my pronunciation?', '2023-01-11 12:06:00'),
(10, 'bot', 'Listen closely to native speakers, mimic their intonation, and record yourself for feedback.', '2023-01-11 12:07:00'),
(10, 'user', 'What’s a fun way to practice?', '2023-01-11 12:08:00'),
(10, 'bot', 'Watch movies, read books, or play games in your target language to make learning enjoyable.', '2023-01-11 12:09:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 11: Finance Tips
(11, 'user', 'How can I save money each month?', '2023-01-12 12:00:00'),
(11, 'bot', 'Track your expenses, cut unnecessary spending, and set automatic transfers to a savings account monthly.', '2023-01-12 12:01:00'),
(11, 'user', 'What’s the 50/30/20 rule?', '2023-01-12 12:02:00'),
(11, 'bot', 'It’s a budgeting strategy: 50% on needs, 30% on wants, and 20% towards savings or paying debt.', '2023-01-12 12:03:00'),
(11, 'user', 'How do I start investing?', '2023-01-12 12:04:00'),
(11, 'bot', 'Begin with low-cost index funds or ETFs, research the market, and consider speaking with a financial advisor.', '2023-01-12 12:05:00'),
(11, 'user', 'Should I have an emergency fund?', '2023-01-12 12:06:00'),
(11, 'bot', 'Absolutely. Aim to save 3 to 6 months’ worth of living expenses in an accessible account.', '2023-01-12 12:07:00'),
(11, 'user', 'How can I improve my credit score?', '2023-01-12 12:08:00'),
(11, 'bot', 'Pay bills on time, reduce credit usage below 30%, and avoid opening too many new accounts quickly.', '2023-01-12 12:09:00'),

-- Session 12: Pet Care
(12, 'user', 'How often should I feed my dog?', '2023-01-13 12:00:00'),
(12, 'bot', 'Adult dogs usually eat twice a day, but it depends on their breed, size, and health conditions.', '2023-01-13 12:01:00'),
(12, 'user', 'What vaccinations does a cat need?', '2023-01-13 12:02:00'),
(12, 'bot', 'Core vaccines include rabies, FVRCP (feline viral rhinotracheitis, calicivirus, and panleukopenia).', '2023-01-13 12:03:00'),
(12, 'user', 'How do I groom my rabbit?', '2023-01-13 12:04:00'),
(12, 'bot', 'Brush your rabbit weekly to prevent matting, and trim nails monthly; avoid bathing unless necessary.', '2023-01-13 12:05:00'),
(12, 'user', 'Can dogs eat human food?', '2023-01-13 12:06:00'),
(12, 'bot', 'Some human foods are safe, like carrots and rice, but avoid chocolate, onions, grapes, and anything toxic.', '2023-01-13 12:07:00'),
(12, 'user', 'How can I tell if my pet is sick?', '2023-01-13 12:08:00'),
(12, 'bot', 'Watch for changes in appetite, behavior, bathroom habits, or energy levels; consult a vet if unsure.', '2023-01-13 12:09:00'),

-- Session 13: Car Maintenance
(13, 'user', 'How often should I change my oil?', '2023-01-14 12:00:00'),
(13, 'bot', 'Every 5,000 to 7,500 miles for most vehicles, but always check your car’s manual for specifics.', '2023-01-14 12:01:00'),
(13, 'user', 'What tire pressure is best?', '2023-01-14 12:02:00'),
(13, 'bot', 'Check the sticker on your driver’s door or the manual—usually between 32–35 PSI for most cars.', '2023-01-14 12:03:00'),
(13, 'user', 'How do I know if brakes need service?', '2023-01-14 12:04:00'),
(13, 'bot', 'Squeaking, grinding, or reduced braking performance are signs your brake pads may need replacing.', '2023-01-14 12:05:00'),
(13, 'user', 'Should I rotate my tires?', '2023-01-14 12:06:00'),
(13, 'bot', 'Yes, rotating your tires every 6,000 to 8,000 miles ensures even wear and longer tire life.', '2023-01-14 12:07:00'),
(13, 'user', 'How do I prepare my car for winter?', '2023-01-14 12:08:00'),
(13, 'bot', 'Check antifreeze levels, battery health, tire tread, and keep an emergency kit in the trunk.', '2023-01-14 12:09:00'),

-- Session 14: Home Decor
(14, 'user', 'How can I make a small room look bigger?', '2023-01-15 12:00:00'),
(14, 'bot', 'Use light colors, mirrors, and multi-functional furniture to create the illusion of space.', '2023-01-15 12:01:00'),
(14, 'user', 'What’s trending in home decor?', '2023-01-15 12:02:00'),
(14, 'bot', 'Natural materials, earthy tones, minimalism, and vintage accents are currently in style.', '2023-01-15 12:03:00'),
(14, 'user', 'How do I choose a color palette?', '2023-01-15 12:04:00'),
(14, 'bot', 'Start with a neutral base and add two to three complementary colors for balance and harmony.', '2023-01-15 12:05:00'),
(14, 'user', 'Should I DIY furniture?', '2023-01-15 12:06:00'),
(14, 'bot', 'DIY can be cost-effective and personal, but make sure you have the right tools and time.', '2023-01-15 12:07:00'),
(14, 'user', 'How do I decorate on a budget?', '2023-01-15 12:08:00'),
(14, 'bot', 'Thrift stores, upcycling, DIY decor, and rearranging existing items can refresh your space affordably.', '2023-01-15 12:09:00'),

-- Session 15: Study Hacks
(15, 'user', 'How can I focus better while studying?', '2023-01-16 12:00:00'),
(15, 'bot', 'Eliminate distractions, use the Pomodoro technique, and create a quiet, well-lit study space.', '2023-01-16 12:01:00'),
(15, 'user', 'What’s a good study schedule?', '2023-01-16 12:02:00'),
(15, 'bot', 'Break your day into focused blocks, assign subjects per block, and include regular breaks to avoid burnout.', '2023-01-16 12:03:00'),
(15, 'user', 'Any memorization techniques?', '2023-01-16 12:04:00'),
(15, 'bot', 'Use mnemonics, flashcards, spaced repetition, and teach the material to someone else for better recall.', '2023-01-16 12:05:00'),
(15, 'user', 'Should I study with music?', '2023-01-16 12:06:00'),
(15, 'bot', 'Instrumental or classical music can help some people focus, but silence works better for others.', '2023-01-16 12:07:00'),
(15, 'user', 'How do I avoid procrastination?', '2023-01-16 12:08:00'),
(15, 'bot', 'Set clear goals, break tasks into smaller parts, and reward yourself after each study session.', '2023-01-16 12:09:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 16: Gaming Trends
(16, 'user', 'What are the top gaming trends in 2023?', '2023-01-17 12:00:00'),
(16, 'bot', 'Cloud gaming, cross-platform play, and the rise of indie games are shaping the gaming industry in 2023.', '2023-01-17 12:01:00'),
(16, 'user', 'Is VR gaming getting more popular?', '2023-01-17 12:02:00'),
(16, 'bot', 'Yes, especially with new headsets like the Meta Quest 3 offering better graphics and immersive experiences.', '2023-01-17 12:03:00'),
(16, 'user', 'What’s the deal with blockchain games?', '2023-01-17 12:04:00'),
(16, 'bot', 'Blockchain games use NFTs and tokens, allowing players to own in-game assets and trade them.', '2023-01-17 12:05:00'),
(16, 'user', 'Are mobile games still growing?', '2023-01-17 12:06:00'),
(16, 'bot', 'Absolutely. Mobile games dominate the market due to accessibility and constant innovation.', '2023-01-17 12:07:00'),
(16, 'user', 'What platforms support cloud gaming?', '2023-01-17 12:08:00'),
(16, 'bot', 'Platforms like Xbox Cloud Gaming, NVIDIA GeForce Now, and Google Stadia (until its shutdown) offered services.', '2023-01-17 12:09:00'),

-- Session 17: Book Summaries
(17, 'user', 'Can you summarize “Atomic Habits”?', '2023-01-18 12:00:00'),
(17, 'bot', 'It teaches how small consistent habits lead to big results by focusing on identity and systems over goals.', '2023-01-18 12:01:00'),
(17, 'user', 'What’s the message in “The Alchemist”?', '2023-01-18 12:02:00'),
(17, 'bot', 'Follow your dreams and personal legend. The universe helps those who truly pursue their destiny.', '2023-01-18 12:03:00'),
(17, 'user', 'Summarize “Deep Work” by Cal Newport.', '2023-01-18 12:04:00'),
(17, 'bot', 'It emphasizes focused, undistracted work as the key to mastering complex tasks in a distracted world.', '2023-01-18 12:05:00'),
(17, 'user', 'What is “Sapiens” about?', '2023-01-18 12:06:00'),
(17, 'bot', 'It explores the history of humankind, how myths and cooperation shaped civilizations and our future.', '2023-01-18 12:07:00'),
(17, 'user', 'Should I read “Start With Why”?', '2023-01-18 12:08:00'),
(17, 'bot', 'Yes, especially if you want to understand how purpose and clarity of vision inspire leadership and action.', '2023-01-18 12:09:00'),

-- Session 18: Art Techniques
(18, 'user', 'What are some basic art techniques?', '2023-01-19 12:00:00'),
(18, 'bot', 'Shading, perspective, blending, hatching, and color theory are foundational techniques for beginners.', '2023-01-19 12:01:00'),
(18, 'user', 'How do I improve my sketching?', '2023-01-19 12:02:00'),
(18, 'bot', 'Practice gesture drawing daily, focus on proportions, and study anatomy to enhance your sketches.', '2023-01-19 12:03:00'),
(18, 'user', 'Is digital art harder than traditional?', '2023-01-19 12:04:00'),
(18, 'bot', 'Each has a learning curve. Digital offers flexibility; traditional builds strong foundational control.', '2023-01-19 12:05:00'),
(18, 'user', 'What tools do digital artists use?', '2023-01-19 12:06:00'),
(18, 'bot', 'Popular tools include drawing tablets (like Wacom), software like Photoshop, Procreate, and Clip Studio Paint.', '2023-01-19 12:07:00'),
(18, 'user', 'Can I learn art without school?', '2023-01-19 12:08:00'),
(18, 'bot', 'Absolutely. Online tutorials, books, and consistent practice can build your skills without formal education.', '2023-01-19 12:09:00'),

-- Session 19: Job Interview
(19, 'user', 'How should I prepare for an interview?', '2023-01-20 12:00:00'),
(19, 'bot', 'Research the company, practice common questions, and prepare examples using the STAR method.', '2023-01-20 12:01:00'),
(19, 'user', 'What’s the STAR method?', '2023-01-20 12:02:00'),
(19, 'bot', 'It stands for Situation, Task, Action, and Result—a way to structure answers with clear context.', '2023-01-20 12:03:00'),
(19, 'user', 'What should I wear?', '2023-01-20 12:04:00'),
(19, 'bot', 'Dress professionally, matching the company’s culture. When unsure, go formal—it’s safer.', '2023-01-20 12:05:00'),
(19, 'user', 'Should I ask questions at the end?', '2023-01-20 12:06:00'),
(19, 'bot', 'Definitely. Ask about company culture, team dynamics, and growth opportunities to show engagement.', '2023-01-20 12:07:00'),
(19, 'user', 'How do I follow up after?', '2023-01-20 12:08:00'),
(19, 'bot', 'Send a thank-you email within 24 hours, expressing appreciation and reiterating your interest.', '2023-01-20 12:09:00'),

-- Session 20: Music Theory
(20, 'user', 'What are the basic elements of music?', '2023-01-21 12:00:00'),
(20, 'bot', 'Melody, harmony, rhythm, dynamics, and form are the foundational elements of music theory.', '2023-01-21 12:01:00'),
(20, 'user', 'What’s a key signature?', '2023-01-21 12:02:00'),
(20, 'bot', 'A key signature tells you the scale of a piece and which notes are consistently sharp or flat.', '2023-01-21 12:03:00'),
(20, 'user', 'What are intervals?', '2023-01-21 12:04:00'),
(20, 'bot', 'Intervals are the distances between two pitches. Understanding them is crucial for reading and writing music.', '2023-01-21 12:05:00'),
(20, 'user', 'How do I build chords?', '2023-01-21 12:06:00'),
(20, 'bot', 'Chords are built by stacking thirds on scale degrees—major, minor, diminished, and augmented.', '2023-01-21 12:07:00'),
(20, 'user', 'What is time signature?', '2023-01-21 12:08:00'),
(20, 'bot', 'It defines the number of beats per measure and the note value that receives one beat, like 4/4 or 3/4.', '2023-01-21 12:09:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 21: Weather Update
(21, 'user', 'What’s the forecast for New York today?', '2023-01-22 12:00:00'),
(21, 'bot', 'Today in New York, expect partly cloudy skies with a high of 68°F and a chance of light showers in the evening.', '2023-01-22 12:01:00'),
(21, 'user', 'Is there a storm expected this week?', '2023-01-22 12:02:00'),
(21, 'bot', 'Yes, a cold front may bring thunderstorms on Thursday, especially in the late afternoon.', '2023-01-22 12:03:00'),
(21, 'user', 'How about the weekend weather?', '2023-01-22 12:04:00'),
(21, 'bot', 'The weekend will be sunny and mild, with temperatures in the low 70s—perfect for outdoor activities.', '2023-01-22 12:05:00'),

-- Session 22: Relationship Talk
(22, 'user', 'How do I improve communication in my relationship?', '2023-01-23 12:00:00'),
(22, 'bot', 'Open, honest conversation without judgment is key. Practice active listening and express needs clearly.', '2023-01-23 12:01:00'),
(22, 'user', 'We argue often—what can help?', '2023-01-23 12:02:00'),
(22, 'bot', 'Understand each other’s triggers, take breaks during conflicts, and revisit the issue calmly later.', '2023-01-23 12:03:00'),
(22, 'user', 'Is couple therapy worth it?', '2023-01-23 12:04:00'),
(22, 'bot', 'Yes, therapy can provide tools to resolve conflicts, improve empathy, and build stronger connections.', '2023-01-23 12:05:00'),

-- Session 23: Mental Health
(23, 'user', 'How do I know if I need therapy?', '2023-01-24 12:00:00'),
(23, 'bot', 'If you feel overwhelmed, anxious, or struggle to function daily, therapy can provide meaningful support.', '2023-01-24 12:01:00'),
(23, 'user', 'Can stress affect physical health?', '2023-01-24 12:02:00'),
(23, 'bot', 'Yes, chronic stress can lead to headaches, digestive issues, high blood pressure, and sleep problems.', '2023-01-24 12:03:00'),
(23, 'user', 'How can I manage anxiety naturally?', '2023-01-24 12:04:00'),
(23, 'bot', 'Try deep breathing, regular exercise, healthy sleep, reducing caffeine, and mindfulness techniques.', '2023-01-24 12:05:00'),

-- Session 24: Gardening Tips
(24, 'user', 'When should I start planting vegetables?', '2023-01-25 12:00:00'),
(24, 'bot', 'Start cool-season crops in early spring. Warm-season vegetables can be planted after the last frost.', '2023-01-25 12:01:00'),
(24, 'user', 'What’s good soil for gardening?', '2023-01-25 12:02:00'),
(24, 'bot', 'Loamy soil rich in organic matter with good drainage and pH between 6.0 to 7.0 is ideal.', '2023-01-25 12:03:00'),
(24, 'user', 'How often should I water my plants?', '2023-01-25 12:04:00'),
(24, 'bot', 'Most garden plants need about 1 inch of water weekly. Adjust based on climate and soil drainage.', '2023-01-25 12:05:00'),

-- Session 25: Fashion Trends
(25, 'user', 'What are the current fashion trends in 2023?', '2023-01-26 12:00:00'),
(25, 'bot', 'This year’s trends include oversized blazers, sustainable fabrics, bold colors, and retro-inspired styles.', '2023-01-26 12:01:00'),
(25, 'user', 'Are skinny jeans still in?', '2023-01-26 12:02:00'),
(25, 'bot', 'Skinny jeans have taken a backseat to wide-leg and baggy fits, though they remain a classic staple.', '2023-01-26 12:03:00'),
(25, 'user', 'What colors are trending?', '2023-01-26 12:04:00'),
(25, 'bot', 'Vibrant shades like lime green, cobalt blue, and lavender are trending, along with earthy neutrals.', '2023-01-26 12:05:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 26: Yoga Practice
(26, 'user', 'I’m new to yoga. Where should I start?', '2023-01-27 12:00:00'),
(26, 'bot', 'Begin with Hatha or Vinyasa yoga. Focus on learning basic poses, breathing, and building consistency.', '2023-01-27 12:01:00'),
(26, 'user', 'How often should I do yoga?', '2023-01-27 12:02:00'),
(26, 'bot', 'Start with 2–3 sessions per week. As your body adapts, you can gradually increase the frequency.', '2023-01-27 12:03:00'),
(26, 'user', 'Can yoga help with stress?', '2023-01-27 12:04:00'),
(26, 'bot', 'Absolutely. Yoga promotes relaxation, deep breathing, and mindfulness, all effective for stress relief.', '2023-01-27 12:05:00'),

-- Session 27: Photography Guide
(27, 'user', 'What’s the best camera for beginners?', '2023-01-28 12:00:00'),
(27, 'bot', 'A DSLR like Canon EOS Rebel or mirrorless options like Sony a6000 are great for learning photography.', '2023-01-28 12:01:00'),
(27, 'user', 'What’s the exposure triangle?', '2023-01-28 12:02:00'),
(27, 'bot', 'It’s the relationship between ISO, aperture, and shutter speed. Mastering it is key to proper exposure.', '2023-01-28 12:03:00'),
(27, 'user', 'How can I take sharp photos?', '2023-01-28 12:04:00'),
(27, 'bot', 'Use faster shutter speeds, proper focus points, and a tripod to reduce blur from hand movements.', '2023-01-28 12:05:00'),

-- Session 28: Legal Questions
(28, 'user', 'Do I need a lawyer for writing a will?', '2023-01-29 12:00:00'),
(28, 'bot', 'While you can write a will yourself, a lawyer ensures it’s legally sound and aligns with your state laws.', '2023-01-29 12:01:00'),
(28, 'user', 'What’s the difference between civil and criminal law?', '2023-01-29 12:02:00'),
(28, 'bot', 'Civil law handles disputes between individuals. Criminal law deals with offenses against the state.', '2023-01-29 12:03:00'),
(28, 'user', 'Is verbal agreement legally binding?', '2023-01-29 12:04:00'),
(28, 'bot', 'Yes, in many cases, but it’s harder to prove. Written contracts are strongly recommended for clarity.', '2023-01-29 12:05:00'),

-- Session 29: Marketing Strategies
(29, 'user', 'What’s a good digital marketing strategy for startups?', '2023-01-30 12:00:00'),
(29, 'bot', 'Focus on content marketing, SEO, social media, and paid ads. Consistency and analytics are key.', '2023-01-30 12:01:00'),
(29, 'user', 'How effective is email marketing?', '2023-01-30 12:02:00'),
(29, 'bot', 'It’s still one of the highest ROI tools. Personalized, value-driven emails can nurture loyal customers.', '2023-01-30 12:03:00'),
(29, 'user', 'Should I hire a marketing agency?', '2023-01-30 12:04:00'),
(29, 'bot', 'If you lack time or expertise, yes. Agencies offer strategic direction, tools, and measurable results.', '2023-01-30 12:05:00'),

-- Session 30: AI Insights
(30, 'user', 'How is AI changing the job market?', '2023-01-31 12:00:00'),
(30, 'bot', 'AI automates repetitive tasks, creates demand for new skills, and is reshaping industries like finance and healthcare.', '2023-01-31 12:01:00'),
(30, 'user', 'Can AI replace creative work?', '2023-01-31 12:02:00'),
(30, 'bot', 'AI assists creativity but lacks human emotion. It complements, rather than replaces, creative professionals.', '2023-01-31 12:03:00'),
(30, 'user', 'What’s the future of AI in education?', '2023-01-31 12:04:00'),
(30, 'bot', 'AI can personalize learning, automate grading, and offer real-time feedback tailored to each student’s needs.', '2023-01-31 12:05:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 31: Homework Help
(31, 'user', 'Can you help me with my algebra homework?', '2023-02-01 12:00:00'),
(31, 'bot', 'Of course! Please share the algebra question, and I’ll guide you through the steps to solve it.', '2023-02-01 12:01:00'),
(31, 'user', 'How do I solve 2x + 3 = 11?', '2023-02-01 12:02:00'),
(31, 'bot', 'First, subtract 3 from both sides: 2x = 8. Then divide by 2 to get x = 4.', '2023-02-01 12:03:00'),
(31, 'user', 'Thanks! That makes sense.', '2023-02-01 12:04:00'),
(31, 'bot', 'Glad I could help! Let me know if you have more questions.', '2023-02-01 12:05:00'),

-- Session 32: Diet Plans
(32, 'user', 'Can you suggest a diet for weight loss?', '2023-02-02 12:00:00'),
(32, 'bot', 'Sure. Focus on high-protein, low-carb meals, lots of veggies, whole grains, and healthy fats.', '2023-02-02 12:01:00'),
(32, 'user', 'How many meals should I eat daily?', '2023-02-02 12:02:00'),
(32, 'bot', 'Three balanced meals with 1–2 healthy snacks are ideal. Avoid skipping meals to maintain energy.', '2023-02-02 12:03:00'),
(32, 'user', 'What about cheat days?', '2023-02-02 12:04:00'),
(32, 'bot', 'Occasional cheat days can help with motivation. Just don’t overdo it, and get back on track afterward.', '2023-02-02 12:05:00'),

-- Session 33: Travel Budget
(33, 'user', 'How do I plan a trip on a tight budget?', '2023-02-03 12:00:00'),
(33, 'bot', 'Start with a destination that offers good value. Book flights in advance and look for budget stays.', '2023-02-03 12:01:00'),
(33, 'user', 'What about food expenses?', '2023-02-03 12:02:00'),
(33, 'bot', 'Eat at local diners or cook if possible. Avoid tourist hotspots to save money and enjoy authentic meals.', '2023-02-03 12:03:00'),
(33, 'user', 'Any apps for travel budgeting?', '2023-02-03 12:04:00'),
(33, 'bot', 'Yes, apps like Trail Wallet or TravelSpend help track expenses and stick to your travel budget.', '2023-02-03 12:05:00'),

-- Session 34: Insurance Guide
(34, 'user', 'Do I need life insurance in my 20s?', '2023-02-04 12:00:00'),
(34, 'bot', 'If you have dependents or debt, yes. Premiums are cheaper when you’re young and healthy.', '2023-02-04 12:01:00'),
(34, 'user', 'What’s the difference between term and whole life insurance?', '2023-02-04 12:02:00'),
(34, 'bot', 'Term provides coverage for a fixed time. Whole life lasts your lifetime and builds cash value over time.', '2023-02-04 12:03:00'),
(34, 'user', 'Is renter’s insurance necessary?', '2023-02-04 12:04:00'),
(34, 'bot', 'Yes, it protects your belongings from theft, fire, or damage in rental properties.', '2023-02-04 12:05:00'),

-- Session 35: Medical Questions
(35, 'user', 'What’s the normal range for blood pressure?', '2023-02-05 12:00:00'),
(35, 'bot', 'A normal reading is around 120/80 mmHg. Above 130/80 is considered elevated or hypertensive.', '2023-02-05 12:01:00'),
(35, 'user', 'How can I boost my immune system?', '2023-02-05 12:02:00'),
(35, 'bot', 'Eat a balanced diet, sleep well, exercise, manage stress, and avoid smoking or excessive drinking.', '2023-02-05 12:03:00'),
(35, 'user', 'When should I see a doctor for a cold?', '2023-02-05 12:04:00'),
(35, 'bot', 'If symptoms last more than 10 days, worsen over time, or include high fever or shortness of breath.', '2023-02-05 12:05:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 36: Fitness Routine
(36, 'user', 'How do I start a basic fitness routine?', '2023-02-06 12:00:00'),
(36, 'bot', 'Begin with 3 days a week: 30 minutes of cardio, strength training, and flexibility exercises.', '2023-02-06 12:01:00'),
(36, 'user', 'Should I focus more on weights or cardio?', '2023-02-06 12:02:00'),
(36, 'bot', 'It depends on your goals. For fat loss, mix both. For muscle gain, prioritize strength training.', '2023-02-06 12:03:00'),
(36, 'user', 'How can I stay motivated?', '2023-02-06 12:04:00'),
(36, 'bot', 'Set achievable goals, track progress, switch routines regularly, and celebrate small victories.', '2023-02-06 12:05:00'),

-- Session 37: Writing Tips
(37, 'user', 'How do I overcome writer’s block?', '2023-02-07 12:00:00'),
(37, 'bot', 'Take short breaks, write anything without judgment, change your environment, or switch topics.', '2023-02-07 12:01:00'),
(37, 'user', 'What’s the best way to start an essay?', '2023-02-07 12:02:00'),
(37, 'bot', 'Begin with a hook: a question, quote, or bold statement. Then provide context and your thesis.', '2023-02-07 12:03:00'),
(37, 'user', 'How can I improve my vocabulary?', '2023-02-07 12:04:00'),
(37, 'bot', 'Read daily, use a vocabulary app, write regularly, and look up unfamiliar words as you encounter them.', '2023-02-07 12:05:00'),

-- Session 38: Startup Ideas
(38, 'user', 'How do I come up with a startup idea?', '2023-02-08 12:00:00'),
(38, 'bot', 'Identify daily problems or inefficiencies. Focus on areas where you have experience or passion.', '2023-02-08 12:01:00'),
(38, 'user', 'Should I build an MVP first?', '2023-02-08 12:02:00'),
(38, 'bot', 'Yes. An MVP helps validate your idea with minimal cost before investing more time or resources.', '2023-02-08 12:03:00'),
(38, 'user', 'How do I find co-founders?', '2023-02-08 12:04:00'),
(38, 'bot', 'Look in your network, startup communities, hackathons, or online platforms like CoFoundersLab.', '2023-02-08 12:05:00'),

-- Session 39: Parenting Advice
(39, 'user', 'How do I handle toddler tantrums?', '2023-02-09 12:00:00'),
(39, 'bot', 'Stay calm, acknowledge their feelings, offer choices, and distract with engaging activities.', '2023-02-09 12:01:00'),
(39, 'user', 'Is screen time harmful for young children?', '2023-02-09 12:02:00'),
(39, 'bot', 'Too much can be, but limited, high-quality content under parental guidance is generally fine.', '2023-02-09 12:03:00'),
(39, 'user', 'How can I build a strong bond with my child?', '2023-02-09 12:04:00'),
(39, 'bot', 'Spend quality time, listen actively, create routines, and show love through words and actions.', '2023-02-09 12:05:00'),

-- Session 40: Home Security
(40, 'user', 'How can I make my home more secure?', '2023-02-10 12:00:00'),
(40, 'bot', 'Install smart locks, motion-sensor lights, a security system, and use cameras at entry points.', '2023-02-10 12:01:00'),
(40, 'user', 'Are DIY security systems reliable?', '2023-02-10 12:02:00'),
(40, 'bot', 'Yes, many modern systems like Ring or SimpliSafe are effective and easy to install yourself.', '2023-02-10 12:03:00'),
(40, 'user', 'Should I tell neighbors when going on vacation?', '2023-02-10 12:04:00'),
(40, 'bot', 'Definitely. Trusted neighbors can keep an eye on your property and collect your mail.', '2023-02-10 12:05:00');

INSERT INTO chat_messages (session_id, sender, message, created_at) VALUES
-- Session 41: Cybersecurity Basics
(41, 'user', 'What is phishing in cybersecurity?', '2023-02-11 12:00:00'),
(41, 'bot', 'Phishing is a cyberattack where attackers trick you into revealing sensitive info like passwords.', '2023-02-11 12:01:00'),
(41, 'user', 'How can I protect myself from phishing?', '2023-02-11 12:02:00'),
(41, 'bot', 'Never click suspicious links, verify sender emails, use multi-factor authentication, and keep software updated.', '2023-02-11 12:03:00'),
(41, 'user', 'Is public Wi-Fi safe?', '2023-02-11 12:04:00'),
(41, 'bot', 'Public Wi-Fi can be risky. Use VPNs and avoid accessing sensitive accounts on open networks.', '2023-02-11 12:05:00'),

-- Session 42: CV Writing
(42, 'user', 'How do I write a good CV?', '2023-02-12 12:00:00'),
(42, 'bot', 'Focus on clear structure, relevant experience, quantifiable achievements, and tailored keywords.', '2023-02-12 12:01:00'),
(42, 'user', 'Should I include hobbies?', '2023-02-12 12:02:00'),
(42, 'bot', 'Only if they relate to the job or demonstrate useful skills like teamwork or leadership.', '2023-02-12 12:03:00'),
(42, 'user', 'How long should my CV be?', '2023-02-12 12:04:00'),
(42, 'bot', 'Ideally one page for early career, max two pages for extensive experience.', '2023-02-12 12:05:00'),

-- Session 43: Cooking Tips
(43, 'user', 'How can I prevent food from sticking to the pan?', '2023-02-13 12:00:00'),
(43, 'bot', 'Use non-stick pans, preheat properly, add oil or butter before cooking, and avoid overcrowding.', '2023-02-13 12:01:00'),
(43, 'user', 'What’s the secret to fluffy rice?', '2023-02-13 12:02:00'),
(43, 'bot', 'Rinse rice before cooking, use the right water ratio, and avoid stirring during cooking.', '2023-02-13 12:03:00'),
(43, 'user', 'How do I know when meat is fully cooked?', '2023-02-13 12:04:00'),
(43, 'bot', 'Use a meat thermometer; different meats have different safe temperatures for doneness.', '2023-02-13 12:05:00');

