package org.example.sudoku;

public record MoveResult(boolean accepted, String message) {
    public static MoveResult success() {
        return new MoveResult(true, "Move accepted.");
    }

    public static MoveResult failure(String message) {
        return new MoveResult(false, message);
    }
}
