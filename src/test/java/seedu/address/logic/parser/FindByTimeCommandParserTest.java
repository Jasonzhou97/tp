package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.TimeMatchesPredicate;

public class FindByTimeCommandParserTest {

    private final FindByTimeCommandParser parser = new FindByTimeCommandParser();

    @Test
    public void parse_validTime_returnsFindByTimeCommand() throws Exception {
        String input = "1400";
        LocalTime expectedTime = LocalTime.of(14, 0);
        FindByTimeCommand expectedCommand = new FindByTimeCommand(new TimeMatchesPredicate(expectedTime));

        FindByTimeCommand result = parser.parse(input);

        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_validTimeWithWhitespace_returnsFindByTimeCommand() throws Exception {
        String input = "   0930  ";
        LocalTime expectedTime = LocalTime.of(9, 30);
        FindByTimeCommand expectedCommand = new FindByTimeCommand(new TimeMatchesPredicate(expectedTime));

        FindByTimeCommand result = parser.parse(input);

        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "   ";

        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByTimeCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String input = "2pm";

        assertThrows(Exception.class, () -> parser.parse(input)); // could be DateTimeParseException or wrapped
    }

    @Test
    public void parse_nonNumeric_throwsParseException() {
        String input = "abcd";

        assertThrows(Exception.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidHourMinute_throwsParseException() {
        String input = "2561"; // Invalid time

        assertThrows(Exception.class, () -> parser.parse(input));
    }
}
