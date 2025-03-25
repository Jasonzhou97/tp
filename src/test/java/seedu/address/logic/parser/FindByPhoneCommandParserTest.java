package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByPhoneCommand;
import seedu.address.model.reservation.PhoneContainsKeywordsPredicate;

public class FindByPhoneCommandParserTest {

    private FindByPhoneCommandParser parser = new FindByPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByPhoneCommand() {
        // no leading or trailing whitespaces
        FindByPhoneCommand expectedFindByPhoneCommand =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "1234")));
        assertParseSuccess(parser, "98765432 1234", expectedFindByPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 98765432 \n \t 1234  \t", expectedFindByPhoneCommand);
    }
}
