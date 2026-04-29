package org.example.sudoku;

public record Puzzle(int[][] givens, int[][] solution) {
    public Puzzle {
        validateGrid(givens, "givens");
        validateGrid(solution, "solution");
        givens = copyOf(givens);
        solution = copyOf(solution);
    }

    public static int[][] copyOf(int[][] source) {
        int[][] copy = new int[SudokuBoard.SIZE][SudokuBoard.SIZE];
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            System.arraycopy(source[row], 0, copy[row], 0, SudokuBoard.SIZE);
        }
        return copy;
    }

    private static void validateGrid(int[][] grid, String name) {
        if (grid == null || grid.length != SudokuBoard.SIZE) {
            throw new IllegalArgumentException(name + " must contain 9 rows.");
        }
        for (int[] row : grid) {
            if (row == null || row.length != SudokuBoard.SIZE) {
                throw new IllegalArgumentException(name + " must contain 9 columns per row.");
            }
        }
    }
}
