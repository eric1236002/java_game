# Lucky Battle Game

## Overview
Lucky Battle is a turn-based card game where the player battles against a boss. The player uses cards with different attributes to attack the boss, while the boss can either attack the player or heal itself. The game ends when either the player's or the boss's health reaches zero.

## Game Rules
- **Player**: Starts with eight cards and gains two cards each turn. The player has health points.
- **Boss**: Has health points and can attack the player or heal itself.
- **Cards**: Each card has specific attributes and attack power. Some cards have special effects that can enhance attacks or provide additional benefits.
- **Turn-based**: The game is played in turns, starting with the player. Each turn has three phases:
  1. **Player Attack Phase**: The player can attack the boss if they have three cards with the same attribute.
  2. **Boss Attack Phase**: The boss randomly decides to either attack the player or heal itself.
  3. **End Phase**: The game checks if either the player or the boss has won.

## Project Structure
- `Boss.java`: Defines the Boss class with health management methods.
- `BossPanel.java`: Handles the graphical representation and animation of the boss.
- `Player.java`: Defines the Player class with health management and card handling methods.
- `PlayerPanel.java`: Handles the graphical representation and animation of the player.
- `Card.java`: Defines the Card class with attributes and special effects.
- `Game.java`: Manages the game logic, including turns, attacks, and health updates.
- `GamePanel.java`: Handles the main game interface and interactions.
- `PlayerCardPanel.java`: Manages the player's hand of cards and their interactions.
- `StartScreen.java`: Manages the start screen of the game.
- `Background.java`: Handles the background image of the game.
- `Util.java`: Provides utility functions for loading images.
- `run.sh`: Script to compile and run the game.
- `Java card/card.csv`: Contains the card data used in the game.
- `rule.txt`: Contains the game rules in Chinese.

## How to Run
1. Ensure you have Java installed on your system.
2. Navigate to the project directory.
3. Run the following command to compile and start the game:
   ```sh
   ./run.sh
   ```

## Assets
- **Images**: Stored in the `picture` directory.
- **Music**: Stored in the `music` directory.

<!-- ## Gameplay Screenshots
![Start Screen](picture/readme/start_screen.png)
![Battle Screen](picture/readme/battle_screen.png)
![Win Screen](picture/readme/win_screen.png) -->

## Controls
- **Left Click**: Select/Deselect cards in your hand
- **Attack Button**: Confirm card selection and attack the boss
- **Yes/No Button**: Choose whether to restart the game after winning/losing

## Card Effects
Cards can have special effects when used in a combination:
- **First Card Special Effect**: Double the attack value
- **Second Card Special Effect**: Heal the player by 2 HP
- **Third Card Special Effect**: Add 2 to the attack value

## Card Attributes
Cards have different attributes that determine their power:
- **Person Type**: Oriental/Westerner
- **Profession**: Athlete/Scientist/Musician/Actor etc.
- **Title**: National leader/Entrepreneur/Hall of famer etc.

## Scoring System
Attack value is determined by the matched attributes:
1. Person Type Match: 1 point
2. Profession Match: 2 points 
3. Title Match: 3 points

## Sound Effects
The game features various sound effects:
- Background music during battles
- Button click sounds
- Victory/Defeat jingles
- Welcome screen music

## Dependencies
- Java Runtime Environment (JRE) 8 or higher
- Java Swing for GUI
- Java Sound API for audio playback

## Development
To modify or extend the game:
1. Clone the repository
2. Add new card images to `Java card/` directory
3. Update `card.csv` with new card data
4. Add new sound effects to `music/` directory

