package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingRemarkPrefix_throwsParseException() {
        String input = "S1234567A This is a remark without a prefix";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidID_throwsParseException() {
        String input = "INVALID_ID r/This is a remark";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_duplicateRemarkPrefixes_throwsParseException() {
        String input = "S1234567A r/First r/Second";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
