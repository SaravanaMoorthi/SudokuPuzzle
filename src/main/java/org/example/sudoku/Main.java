package org.example.sudoku;

import java.io.InputStreamReader;
import java.io.PrintWriter;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ConsoleSudokuGame game = new ConsoleSudokuGame(
                new PuzzleGenerator(),
                new InputStreamReader(System.in),
                new PrintWriter(System.out, true)
        );
        game.run();
    }
}
