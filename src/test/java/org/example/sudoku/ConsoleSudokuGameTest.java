package org.example.sudoku;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleSudokuGameTest {
    @Test
    void supportsMoveCheckHintAndQuitFlow() {
        String input = String.join(System.lineSeparator(), "A3 4", "check", "hint", "quit");
        StringWriter output = new StringWriter();

        new ConsoleSudokuGame(new FixedPuzzleGenerator(), new StringReader(input), new PrintWriter(output, true)).run();

        String transcript = output.toString();
        assertTrue(transcript.contains("Welcome to Sudoku!"));
        assertTrue(transcript.contains("Move accepted."));
        assertTrue(transcript.contains("No rule violations detected."));
        assertTrue(transcript.contains("Hint: Cell A4 = 6"));
        assertTrue(transcript.contains("Goodbye."));
    }

    private static class FixedPuzzleGenerator extends PuzzleGenerator {
        @Override
        public Puzzle generate() {
            int[][] givens = {
                    {5, 3, 0, 0, 7, 0, 0, 0, 0},
                    {6, 0, 0, 1, 9, 5, 0, 0, 0},
                    {0, 9, 8, 0, 0, 0, 0, 6, 0},
                    {8, 0, 0, 0, 6, 0, 0, 0, 3},
                    {4, 0, 0, 8, 0, 3, 0, 0, 1},
                    {7, 0, 0, 0, 2, 0, 0, 0, 6},
                    {0, 6, 0, 0, 0, 0, 2, 8, 0},
                    {0, 0, 0, 4, 1, 9, 0, 0, 5},
                    {0, 0, 0, 0, 8, 0, 0, 7, 9}
            };
            int[][] solution = {
                    {5, 3, 4, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}
            };
            return new Puzzle(givens, solution);
        }
    }
}
