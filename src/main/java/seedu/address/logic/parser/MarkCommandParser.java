package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.model.reservation.Identification;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkCommandParser implements Parser<MarkCommand> {

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
