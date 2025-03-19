package seedu.address.logic.parser;



import seedu.address.logic.commands.FindCommand;
import seedu.address.model.reservation.Identification;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.StartDate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class MarkCommandParser implements Parser<MarkCommand> {

    @Override
    public MarkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] words = trimmedArgs.split(" ");
        //throws parseException when the args is empty
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        try {
            //if the bookingId is invalid form, may throw error and show markCommand.message_usage
            Identification bookingId = new Identification(new StartDate(words[0]), new Phone(words[1]));
            return new MarkCommand(bookingId);
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE), e);
        }

    }
}
