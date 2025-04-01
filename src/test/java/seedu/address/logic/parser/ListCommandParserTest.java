package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parseForList_nonEmptyArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommandParser.MESSAGE_INVALID_FORMAT);

        assertThrows(ParseException.class, () -> parser.parseForList(" argument"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForList("-a"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForList("   extra   "),
                expectedMessage);
    }

    @Test
    public void parseForListRT_nonEmptyArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommandParser.MESSAGE_INVALID_FORMAT);

        assertThrows(ParseException.class, () -> parser.parseForListRT(" argument"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListRT("-a"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListRT("   extra   "),
                expectedMessage);
    }

    @Test
    public void parseForListRN_nonEmptyArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommandParser.MESSAGE_INVALID_FORMAT);

        assertThrows(ParseException.class, () -> parser.parseForListRN(" argument"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListRN("-a"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListRN("   extra   "),
                expectedMessage);
    }

    @Test
    public void parseForListR_nonEmptyArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommandParser.MESSAGE_INVALID_FORMAT);

        assertThrows(ParseException.class, () -> parser.parseForListR(" argument"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListR("-a"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListR("   extra   "),
                expectedMessage);
    }

    @Test
    public void parseForListP_nonEmptyArguments_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommandParser.MESSAGE_INVALID_FORMAT);

        assertThrows(ParseException.class, () -> parser.parseForListP(" argument"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListP("-a"),
                expectedMessage);
        assertThrows(ParseException.class, () -> parser.parseForListP("   extra   "),
                expectedMessage);
    }

}
