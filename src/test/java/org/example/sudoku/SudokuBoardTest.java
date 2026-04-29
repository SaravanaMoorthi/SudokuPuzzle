package org.example.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuBoardTest {
    @Test
    void rejectsChangesToPrefilledCells() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        MoveResult result = board.place(new Cell(0, 0), 6);

        assertFalse(result.accepted());
        assertEquals("Invalid move. A1 is pre-filled.", result.message());
        assertEquals(5, board.valueAt(new Cell(0, 0)));
    }

    @Test
    void acceptsDuplicateMovesButReportsRowViolationOnCheck() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        MoveResult result = board.place(new Cell(0, 2), 3);

        assertTrue(result.accepted());
        assertEquals("Number 3 already exists in Row A.", board.validate().message());
    }

    @Test
    void reportsColumnViolations() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        board.place(new Cell(2, 0), 5);

        assertEquals("Number 5 already exists in Column 1.", board.validate().message());
    }

    @Test
    void reportsSubgridViolations() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        board.place(new Cell(1, 1), 8);

        assertEquals("Number 8 already exists in the same 3x3 subgrid.", board.validate().message());
    }

    @Test
    void hintRevealsCorrectNumberIntoAnEditableCell() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        Hint hint = board.revealHint().orElseThrow();

        assertEquals("A3", hint.cell().label());
        assertEquals(4, hint.number());
        assertEquals(4, board.valueAt(new Cell(0, 2)));
    }

    @Test
    void rendersEmptyCellsAsUnderscores() {
        SudokuBoard board = new SudokuBoard(samplePuzzle());

        String rendered = board.render();

        assertTrue(rendered.contains("A 5 3 _ _ 7 _ _ _ _"));
        assertFalse(rendered.contains("95"));
    }

    private Puzzle samplePuzzle() {
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
