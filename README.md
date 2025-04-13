# 🛡️ BUNKER – A Post-Apocalyptic Telegram Bot Game

![GitHub last commit](https://img.shields.io/github/last-commit/alterdekim/JBunker)
[![Jenkins Build](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fjenkins.awain.net%2Fjob%2FJBunker%2F)](https://jenkins.awain.net/job/JBunker/)
![GitHub License](https://img.shields.io/github/license/alterdekim/JBunker)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/alterdekim/JBunker)

**Bunker** is a multiplayer text-based survival game hosted on Telegram. Players take on the roles of randomly assigned survivors trapped in a bunker after a global catastrophe. Each game challenges the group to vote on who should be expelled from the bunker while managing limited resources, random events, and hidden player traits.

## 🎮 How to Play

1. **Start a Game**  
   Set up a telegram bot and invite it to your telegram group, then you can bind bot to group using `/setgroup`. To start a game use `/startgame`.

2. **Receive a Profile**  
   Each player is given a unique profile with:
    - Age
    - Gender
    - Profession
    - Health status
    - Luggage contents
    - Hobby

3. **Reveal Traits**  
   Each round, players must reveal one new trait to the group.

4. **Voting Rounds**  
   At the end of each round, players vote on who should be removed from the bunker.

5. **Events & Environment**  
   Each game includes:
    - **Bunker conditions** (e.g. broken air filter, food supply, structural issues)
    - **Player-driven actions** (e.g. steal luggage)
    - **Randomized voting rules** per round (e.g. tie = no expel, reversed voting)

6. **Win or Lose**  
   At the end of the game, the bot evaluates remaining player profiles against the environment to calculate a **survival win rate**. If it's high enough, the group wins. If not—well, better luck next apocalypse.

---

## ⚙️ Features

- 🧠 Dynamic trait-based strategy
- 🎲 Procedural environmental hazards
- 🧍 Player actions and deception
- 🔄 Randomized voting mechanics
- 📊 Automatic win/loss evaluation
- 🤖 Smooth Telegram integration
- ☕ Built with Java + Spring Boot

---

## 🚀 Getting Started (For Developers)

### Requirements
- Java 17+
- Maven
- Telegram Bot Token (via BotFather)
