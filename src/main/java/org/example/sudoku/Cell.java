package org.example.sudoku;

public record Cell(int row, int column) {
    public Cell {
        if (row < 0 || row >= SudokuBoard.SIZE || column < 0 || column >= SudokuBoard.SIZE) {
            throw new IllegalArgumentException("Cell must be inside a 9x9 Sudoku grid.");
        }
    }

    public String label() {
        return "%c%d".formatted((char) ('A' + row), column + 1);
    }
}
