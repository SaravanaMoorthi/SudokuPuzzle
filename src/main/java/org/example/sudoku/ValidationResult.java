package org.example.sudoku;

public record ValidationResult(boolean valid, String message) {
    public static ValidationResult success() {
        return new ValidationResult(true, "No rule violations detected.");
    }

    public static ValidationResult failure(String message) {
        return new ValidationResult(false, message);
    }
}
