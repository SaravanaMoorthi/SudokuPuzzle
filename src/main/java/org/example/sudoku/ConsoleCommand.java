package org.example.sudoku;

public sealed interface ConsoleCommand permits ConsoleCommand.Place, ConsoleCommand.Clear, ConsoleCommand.HintCommand,
        ConsoleCommand.Check, ConsoleCommand.Quit {
    record Place(Cell cell, int number) implements ConsoleCommand {
    }

    record Clear(Cell cell) implements ConsoleCommand {
    }

    record HintCommand() implements ConsoleCommand {
    }

    record Check() implements ConsoleCommand {
    }

    record Quit() implements ConsoleCommand {
    }
}
