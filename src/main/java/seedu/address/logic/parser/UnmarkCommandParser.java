package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Identification;

/**
 * Parses input arguments and creates a new {@code UnmarkCommand} object.
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code UnmarkCommand} object.
     *
     * @param args The user input arguments.
     * @return A new {@code UnmarkCommand} instance.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public UnmarkCommand parse(String args) throws ParseException {

        try {
            Identification id = ParserUtil.parseID(args);
            return new UnmarkCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE), pe);
        }

    }
}
