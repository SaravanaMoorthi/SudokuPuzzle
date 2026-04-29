# Sudoku Command-Line App

This is a Java command-line Sudoku game. It generates a solved Sudoku grid, removes cells until exactly 30 pre-filled numbers remain, and lets the player enter moves, clear editable cells, request hints, check rule violations, or quit.

## Requirements

- Java 17 or later
- Maven 3.9 or later
- Windows, Linux, or macOS terminal

## Run

```bash
mvn clean test
mvn exec:java -Dexec.mainClass=org.example.sudoku.Main
```

If the Maven Exec plugin is not available locally, run with the standard JDK tools:

```bash
mvn clean package
java -cp target/classes org.example.sudoku.Main
```

## Commands

- `A3 4` places number `4` in row `A`, column `3`.
- `C5 clear` clears row `C`, column `5`.
- `hint` reveals one correct editable cell.
- `check` reports the first row, column, or 3x3 subgrid violation found.
- `quit` exits the game.

## Design Notes

- `PuzzleGenerator` creates complete valid grids by shuffling a known Sudoku pattern, then keeps exactly 30 givens.
- `SudokuBoard` owns grid state, fixed-cell protection, hints, completion checks, and Sudoku rule validation.
- `CommandParser` converts terminal input into typed commands, keeping parsing separate from game state.
- `ConsoleSudokuGame` handles the input/output loop and delegates game rules to the domain classes.

Assumption: generated puzzles are valid and solvable because each puzzle is derived from a complete solution. The generator does not currently prove that a puzzle has a unique solution; that can be added later without changing the command-line flow.
