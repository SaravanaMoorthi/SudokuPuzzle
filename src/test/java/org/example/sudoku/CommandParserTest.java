package org.example.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandParserTest {
    private final CommandParser parser = new CommandParser();

    @Test
    void parsesPlaceCommand() {
        ConsoleCommand.Place command = assertInstanceOf(ConsoleCommand.Place.class, parser.parse("B3 7"));

        assertEquals(new Cell(1, 2), command.cell());
        assertEquals(7, command.number());
    }

    @Test
    void parsesClearCommandCaseInsensitively() {
        ConsoleCommand.Clear command = assertInstanceOf(ConsoleCommand.Clear.class, parser.parse("c5 CLEAR"));

        assertEquals(new Cell(2, 4), command.cell());
    }

    @Test
    void rejectsInvalidCell() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> parser.parse("J1 4"));

        assertEquals("Invalid cell. Use rows A-I and columns 1-9.", ex.getMessage());
    }

    @Test
    void toleratesNullCharactersFromPipedInput() {
        assertInstanceOf(ConsoleCommand.Quit.class, parser.parse("q\u0000u\u0000i\u0000t\u0000"));
    }
}
