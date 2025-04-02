package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.NameContainsKeywordsPredicate;

public class FindByNameCommandParserTest {

    private final FindByNameCommandParser parser = new FindByNameCommandParser();

    @Test
    public void parse_singleKeyword_success() throws Exception {
        String input = "Alice";
        NameContainsKeywordsPredicate expectedPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        FindByNameCommand expectedCommand = new FindByNameCommand(expectedPredicate);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_multipleKeywords_success() throws Exception {
        String input = "Alice Bob Charlie";
        NameContainsKeywordsPredicate expectedPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Charlie"));
        FindByNameCommand expectedCommand = new FindByNameCommand(expectedPredicate);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_inputWithExtraSpaces_success() throws Exception {
        String input = "  Alice   Bob   ";
        NameContainsKeywordsPredicate expectedPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        FindByNameCommand expectedCommand = new FindByNameCommand(expectedPredicate);

        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "   ";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByNameCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
