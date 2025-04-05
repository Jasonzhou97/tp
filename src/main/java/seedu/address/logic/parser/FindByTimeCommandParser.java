package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.FindByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.TimeMatchesPredicate;

/**
 * Parses input argument and creates a new FindByTimeCommand object
 */
public class FindByTimeCommandParser implements Parser<FindByTimeCommand> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    /**
     * Parses the given argument in the context of the FindByTimeCommand
     * and returns a FindByTimeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindByTimeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByTimeCommand.MESSAGE_USAGE));
        }

        String[] timeKeyword = trimmedArgs.split("\\s+");
        if (timeKeyword.length != 1 || !StartTime.isValidTime(timeKeyword[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByTimeCommand.MESSAGE_USAGE));
        }

        LocalTime searchTime = LocalTime.parse(timeKeyword[0], TIME_FORMATTER);
        return new FindByTimeCommand(new TimeMatchesPredicate(searchTime));
    }
}
