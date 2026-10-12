🚀 Quick Start

📋 Prerequisites

🟢 Node.js 18+ and Angular CLI (npm i -g @angular/cli)

☕ JDK 17+ and Maven/Gradle

🔑 An OpenAI and/or Gemini API key

1️⃣ Clone
bash

git clone https://github.com/dhsaipriya25/GenAi-ChatBot.git

cd GenAi-ChatBot

2️⃣ Backend
bash

cd Backend

# set your API keys (see Environment Variables below)
./mvnw spring-boot:run        # or: ./gradlew bootRun

3️⃣ Frontend
bash
# from the project root

npm install

ng serve

👉 Open http://localhost:4200

🔐 Environment Variables

Variable	Description

OPENAI_API_KEY	Your OpenAI API key

GEMINI_API_KEY	Your Google Gemini API key

DB_URL / DB_USER / DB_PASSWORD	Database connection
