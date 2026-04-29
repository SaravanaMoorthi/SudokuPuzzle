package org.example.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator {
    static final int GIVEN_COUNT = 30;
    private final Random random;

    public PuzzleGenerator() {
        this(new Random());
    }

    public PuzzleGenerator(Random random) {
        this.random = random;
    }

    public Puzzle generate() {
        int[][] solution = createSolvedGrid();
        int[][] givens = Puzzle.copyOf(solution);

        List<Cell> cells = new ArrayList<>();
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int column = 0; column < SudokuBoard.SIZE; column++) {
                cells.add(new Cell(row, column));
            }
        }
        Collections.shuffle(cells, random);

        int cellsToClear = SudokuBoard.SIZE * SudokuBoard.SIZE - GIVEN_COUNT;
        for (int i = 0; i < cellsToClear; i++) {
            Cell cell = cells.get(i);
            givens[cell.row()][cell.column()] = SudokuBoard.EMPTY;
        }

        return new Puzzle(givens, solution);
    }

    private int[][] createSolvedGrid() {
        List<Integer> rowBands = shuffled(List.of(0, 1, 2));
        List<Integer> columnBands = shuffled(List.of(0, 1, 2));
        List<Integer> numbers = shuffled(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

        int[] rows = expandedGroups(rowBands);
        int[] columns = expandedGroups(columnBands);
        int[][] grid = new int[SudokuBoard.SIZE][SudokuBoard.SIZE];

        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int column = 0; column < SudokuBoard.SIZE; column++) {
                int patternIndex = (rows[row] * 3 + rows[row] / 3 + columns[column]) % SudokuBoard.SIZE;
                grid[row][column] = numbers.get(patternIndex);
            }
        }
        return grid;
    }

    private int[] expandedGroups(List<Integer> groups) {
        int[] result = new int[SudokuBoard.SIZE];
        int index = 0;
        for (int group : groups) {
            List<Integer> members = shuffled(List.of(0, 1, 2));
            for (int member : members) {
                result[index++] = group * 3 + member;
            }
        }
        return result;
    }

    private <T> List<T> shuffled(List<T> values) {
        List<T> copy = new ArrayList<>(values);
        Collections.shuffle(copy, random);
        return copy;
    }
}
