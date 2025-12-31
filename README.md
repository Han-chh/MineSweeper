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

## Installation and Running

1. Ensure JDK is installed on your system.
2. Navigate to the project directory.
3. Compile the source code:
   ```
   javac -d bin src/com/minesweeper/game/*.java

   xcopy /E /I resources bin (windows)
   or
   mkdir -p bin
   cp -R Images bin/ (Mac)
   
   ```
4. Run the game:
   ```
   java -cp bin com.minesweeper.game.MainGame
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
