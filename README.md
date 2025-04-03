# DrillQuest

## Overview

**DrillQuest** is a 2D mining game developed in Java using **JavaFX** as part of BBM104 – Introduction to Programming Laboratory II. Inspired by the classic "Motherload", the game features a drill machine that explores underground, collects valuable minerals, and avoids lava while managing limited fuel.

---

## Gameplay Features

- 🎮 **Player-Controlled Drill**: Moves with arrow keys, consumes fuel during movement and drilling.
- 🪙 **Valuable Items**: Gems and minerals with weight and monetary value.
- 🌋 **Hazards**: Lava causes instant game over.
- 🧱 **Obstacles**: Unbreakable boulders and gravity-based falling mechanics.
- 💰 **Money System**: Earn money by collecting valuables.
- ⛽ **Fuel Mechanic**: Game ends if fuel runs out. Fuel is consumed constantly, more during drilling.
- 🧭 **Game Over States**:
  - Drill hits lava
  - Fuel depletion

---

## Installation

```bash
git clone https://github.com/bushushow/DrillQuest.git
cd DrillQuest
javac8 *.java
java8 Main
```

## Assets

The game uses a pre-defined set of visual assets located under the `src/assets/` directory. These assets include tile images, player sprites, and environmental elements like soil, lava, and minerals.

All asset loading is hardcoded to assume the following path:


## Usage

After compiling the project, run the game using the following command:

```bash
java8 Main
```
