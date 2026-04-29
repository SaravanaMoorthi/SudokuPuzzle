package org.example.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SudokuBoard {
    public static final int SIZE = 9;
    public static final int EMPTY = 0;

    private final int[][] values;
    private final int[][] solution;
    private final boolean[][] fixed;

    public SudokuBoard(Puzzle puzzle) {
        this.values = Puzzle.copyOf(puzzle.givens());
        this.solution = Puzzle.copyOf(puzzle.solution());
        this.fixed = new boolean[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                fixed[row][column] = values[row][column] != EMPTY;
            }
        }
    }

    public MoveResult place(Cell cell, int number) {
        if (number < 1 || number > 9) {
            return MoveResult.failure("Invalid move. Number must be between 1 and 9.");
        }
        if (isFixed(cell)) {
            return MoveResult.failure("Invalid move. " + cell.label() + " is pre-filled.");
        }
        values[cell.row()][cell.column()] = number;
        return MoveResult.success();
    }

    public MoveResult clear(Cell cell) {
        if (isFixed(cell)) {
            return MoveResult.failure("Invalid move. " + cell.label() + " is pre-filled.");
        }
        values[cell.row()][cell.column()] = EMPTY;
        return MoveResult.success();
    }

    public Optional<Hint> revealHint() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (!fixed[row][column] && values[row][column] != solution[row][column]) {
                    Cell cell = new Cell(row, column);
                    values[row][column] = solution[row][column];
                    return Optional.of(new Hint(cell, solution[row][column]));
                }
            }
        }
        return Optional.empty();
    }

    public ValidationResult validate() {
        Optional<String> rowViolation = findRowViolation();
        if (rowViolation.isPresent()) {
            return ValidationResult.failure(rowViolation.get());
        }

        Optional<String> columnViolation = findColumnViolation();
        if (columnViolation.isPresent()) {
            return ValidationResult.failure(columnViolation.get());
        }

        Optional<String> subgridViolation = findSubgridViolation();
        return subgridViolation.map(ValidationResult::failure)
                .orElseGet(ValidationResult::success);
    }

    public boolean isCompleteAndCorrect() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (values[row][column] != solution[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int valueAt(Cell cell) {
        return values[cell.row()][cell.column()];
    }

    public boolean isFixed(Cell cell) {
        return fixed[cell.row()][cell.column()];
    }

    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append("    1 2 3 4 5 6 7 8 9").append(System.lineSeparator());
        for (int row = 0; row < SIZE; row++) {
            builder.append("  ").append((char) ('A' + row)).append(' ');
            for (int column = 0; column < SIZE; column++) {
                int value = values[row][column];
                if (value == EMPTY) {
                    builder.append('_');
                } else {
                    builder.append(value);
                }
                if (column < SIZE - 1) {
                    builder.append(' ');
                }
            }
            if (row < SIZE - 1) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    private Optional<String> findRowViolation() {
        for (int row = 0; row < SIZE; row++) {
            Optional<Integer> duplicate = duplicateIn(valuesInRow(row));
            if (duplicate.isPresent()) {
                return Optional.of("Number %d already exists in Row %c.".formatted(duplicate.get(), (char) ('A' + row)));
            }
        }
        return Optional.empty();
    }

    private Optional<String> findColumnViolation() {
        for (int column = 0; column < SIZE; column++) {
            Optional<Integer> duplicate = duplicateIn(valuesInColumn(column));
            if (duplicate.isPresent()) {
                return Optional.of("Number %d already exists in Column %d.".formatted(duplicate.get(), column + 1));
            }
        }
        return Optional.empty();
    }

    private Optional<String> findSubgridViolation() {
        for (int startRow = 0; startRow < SIZE; startRow += 3) {
            for (int startColumn = 0; startColumn < SIZE; startColumn += 3) {
                Optional<Integer> duplicate = duplicateIn(valuesInSubgrid(startRow, startColumn));
                if (duplicate.isPresent()) {
                    return Optional.of("Number %d already exists in the same 3x3 subgrid.".formatted(duplicate.get()));
                }
            }
        }
        return Optional.empty();
    }

    private List<Integer> valuesInRow(int row) {
        List<Integer> rowValues = new ArrayList<>();
        for (int column = 0; column < SIZE; column++) {
            rowValues.add(values[row][column]);
        }
        return rowValues;
    }

    private List<Integer> valuesInColumn(int column) {
        List<Integer> columnValues = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            columnValues.add(values[row][column]);
        }
        return columnValues;
    }

    private List<Integer> valuesInSubgrid(int startRow, int startColumn) {
        List<Integer> subgridValues = new ArrayList<>();
        for (int row = startRow; row < startRow + 3; row++) {
            for (int column = startColumn; column < startColumn + 3; column++) {
                subgridValues.add(values[row][column]);
            }
        }
        return subgridValues;
    }

    private Optional<Integer> duplicateIn(List<Integer> numbers) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int number : numbers) {
            if (number == EMPTY) {
                continue;
            }
            if (seen[number]) {
                return Optional.of(number);
            }
            seen[number] = true;
        }
        return Optional.empty();
    }
}
