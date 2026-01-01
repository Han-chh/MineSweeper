# MineSweeper

## Description

A classic Minesweeper game implemented in Java using Swing. Features multiple difficulty levels and time tracking with historical records.

## Features

- Multiple difficulty levels: Simple, Medium, Hard, Custom
- Time tracking for each game
- Historical record keeping
- Win/lose detection
- Intuitive GUI interface
- Record clearing functionality

## Requirements

- Java Development Kit (JDK) 8 or higher
- The jar package is packaged with Java 25.

## Installation and Running

Ensure JDK is installed on your system.

To run the game, use the pre-built JAR file:

```bash
java -jar MineSweeper.jar
```

## How to Play

1. Select a difficulty level
2. Click "START" to begin
3. Left-click to reveal cells
4. Right-click to flag potential mines
5. Avoid clicking on mines
6. Reveal all non-mine cells to win

## Project Structure

- `src/com/minesweeper/game/MainGame.java`: Main entry point and game initialization
- `src/com/minesweeper/game/BeginUI.java`: Difficulty selection and menu interface
- `src/com/minesweeper/game/GameUI.java`: Main game board interface
- `src/com/minesweeper/game/SingleGrid.java`: Individual grid cell component
- `src/com/minesweeper/game/MessageDialogs.java`: Dialog utilities
- `src/com/minesweeper/game/Number.java`: Number display utilities
- `HISTORY`: File for storing game records
- `bin/`: Compiled class files

## Data Storage

Game records are stored in the `HISTORY` file using object serialization.

## Contributing

Contributions are welcome! Please submit issues and pull requests for enhancements.
