package org.example.sudoku;

import java.util.Locale;

public class CommandParser {
    public ConsoleCommand parse(String input) {
        String normalized = input == null ? "" : input.replace("\u0000", "").trim();
        if (normalized.equalsIgnoreCase("hint")) {
            return new ConsoleCommand.HintCommand();
        }
        if (normalized.equalsIgnoreCase("check")) {
            return new ConsoleCommand.Check();
        }
        if (normalized.equalsIgnoreCase("quit")) {
            return new ConsoleCommand.Quit();
        }

        String[] parts = normalized.split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid command. Use A3 4, C5 clear, hint, check, or quit.");
        }

        Cell cell = parseCell(parts[0]);
        if (parts[1].equalsIgnoreCase("clear")) {
            return new ConsoleCommand.Clear(cell);
        }

        try {
            return new ConsoleCommand.Place(cell, Integer.parseInt(parts[1]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid command. Number must be between 1 and 9.");
        }
    }

    private Cell parseCell(String token) {
        String normalized = token.toUpperCase(Locale.ROOT);
        if (!normalized.matches("[A-I][1-9]")) {
            throw new IllegalArgumentException("Invalid cell. Use rows A-I and columns 1-9.");
        }
        int row = normalized.charAt(0) - 'A';
        int column = normalized.charAt(1) - '1';
        return new Cell(row, column);
    }
}
