package org.example.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PuzzleGeneratorTest {
    @Test
    void generatedPuzzleHasThirtyGivensAndValidSolution() {
        Puzzle puzzle = new PuzzleGenerator(new Random(1)).generate();

        assertEquals(30, countGivens(puzzle.givens()));
        assertEquals("No rule violations detected.", new SudokuBoard(new Puzzle(puzzle.solution(), puzzle.solution())).validate().message());
    }

    private int countGivens(int[][] givens) {
        int count = 0;
        for (int[] row : givens) {
            for (int value : row) {
                if (value != SudokuBoard.EMPTY) {
                    count++;
                }
            }
        }
        return count;
    }
}
