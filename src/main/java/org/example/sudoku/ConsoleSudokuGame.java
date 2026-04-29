package org.example.sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UncheckedIOException;

public class ConsoleSudokuGame {
    private static final String PROMPT = "Enter command (e.g., A3 4, C5 clear, hint, check, quit):";

    private final PuzzleGenerator puzzleGenerator;
    private final BufferedReader input;
    private final PrintWriter output;
    private final CommandParser commandParser = new CommandParser();

    public ConsoleSudokuGame(PuzzleGenerator puzzleGenerator, Reader input, PrintWriter output) {
        this.puzzleGenerator = puzzleGenerator;
        this.input = new BufferedReader(input);
        this.output = output;
    }

    public void run() {
        output.println("Welcome to Sudoku!");
        output.println();

        boolean playAgain = true;
        while (playAgain) {
            playAgain = playOnePuzzle();
        }
    }

    private boolean playOnePuzzle() {
        SudokuBoard board = new SudokuBoard(puzzleGenerator.generate());
        output.println("Here is your puzzle:");
        output.println(board.render());
        output.println();

        while (true) {
            output.println(PROMPT);
            String line = readLine();
            if (line == null) {
                return false;
            }

            try {
                ConsoleCommand command = commandParser.parse(line);
                if (command instanceof ConsoleCommand.Quit) {
                    output.println("Goodbye.");
                    return false;
                }
                handleCommand(command, board);
            } catch (IllegalArgumentException ex) {
                output.println(ex.getMessage());
            }

            if (board.isCompleteAndCorrect()) {
                output.println("You have successfully completed the Sudoku puzzle!");
                output.println("Press any key to play again...");
                return readLine() != null;
            }

            output.println();
        }
    }

    private void handleCommand(ConsoleCommand command, SudokuBoard board) {
        if (command instanceof ConsoleCommand.Place place) {
            printMoveResult(board.place(place.cell(), place.number()), board);
            return;
        }
        if (command instanceof ConsoleCommand.Clear clear) {
            printMoveResult(board.clear(clear.cell()), board);
            return;
        }
        if (command instanceof ConsoleCommand.HintCommand) {
            board.revealHint()
                    .ifPresentOrElse(
                            hint -> output.println("Hint: Cell %s = %d".formatted(hint.cell().label(), hint.number())),
                            () -> output.println("No hints available.")
                    );
            return;
        }
        if (command instanceof ConsoleCommand.Check) {
            output.println(board.validate().message());
        }
    }

    private void printMoveResult(MoveResult result, SudokuBoard board) {
        output.println(result.message());
        output.println();
        output.println("Current grid:");
        output.println(board.render());
    }

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
