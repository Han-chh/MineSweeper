# MineSweeper

A Java desktop Minesweeper implementation with multiple difficulty levels, custom boards, recursive reveals, flags, timing, and history.

[中文说明](README_zh.md)

## Overview

This project implements the recognizable Minesweeper loop while exposing the underlying grid algorithms: random mine placement, adjacent-number calculation, recursive opening of empty regions, flag management, win/loss state, and local history.

## Demo

![Animated Minesweeper walkthrough showing board setup, flagging, clues, and recursive reveal](assets/visual-demos/minesweeper-reveal-and-flag.gif)

[Full-resolution MP4 demo](assets/visual-demos/minesweeper-reveal-and-flag.mp4)

The video is recorded directly from the native Swing application and covers difficulty selection, a custom board, history, flagging, timing, number clues, and recursive reveal.

## Screenshot

![A Minesweeper board with an expanded safe region, number clues, and a flag](assets/screenshots/minesweeper-gameplay.png)

## Features

- Simple, medium, hard, and custom configurations
- Random mine and number generation
- Recursive empty-region reveal
- Flag/meta-click interaction
- Remaining-mine counter and timer
- Serialized game history

## Run

The committed JAR was verified with Java 25:

```bash
java -jar MineSweeper.jar
```

## Data note

The application may read and write `HISTORY`. Use a disposable working directory for demos and tests.
