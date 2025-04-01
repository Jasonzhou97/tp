package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListPCommand;
import seedu.address.logic.commands.ListRCommand;
import seedu.address.logic.commands.ListRnCommand;
import seedu.address.logic.commands.ListRtCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    // Define a constant for testing
    private static final Index INDEX_ONE = Index.fromOneBased(1);


    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }


    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        // Test that arguments are not allowed
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listR() throws Exception {
        assertTrue(parser.parseCommand(ListRCommand.COMMAND_WORD) instanceof ListRCommand);
        // Test that arguments are not allowed
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListRCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listP() throws Exception {
        assertTrue(parser.parseCommand(ListPCommand.COMMAND_WORD) instanceof ListPCommand);
        // Test that arguments are not allowed
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListPCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listRt() throws Exception {
        assertTrue(parser.parseCommand(ListRtCommand.COMMAND_WORD) instanceof ListRtCommand);
        // Test that arguments are not allowed
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListRtCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listRn() throws Exception {
        assertTrue(parser.parseCommand(ListRnCommand.COMMAND_WORD) instanceof ListRnCommand);
        // Test that arguments are not allowed
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListRnCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand("unknownCommand"));
    }
}
