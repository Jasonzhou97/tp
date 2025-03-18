package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Identification;

/**
 * Parses input arguments and creates a new {@code MarkCommand} object.
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code MarkCommand} object.
     *
     * @param args The user input arguments.
     * @return A new {@code MarkCommand} instance.
     * @throws ParseException If the input does not conform to the expected format.
     */
    @Override
    public MarkCommand parse(String args) throws ParseException {

        try {
            Identification id = ParserUtil.parseID(args);
            return new MarkCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE), pe);
        }

    }
}
