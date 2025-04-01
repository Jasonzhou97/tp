package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListPCommand;
import seedu.address.logic.commands.ListRCommand;
import seedu.address.logic.commands.ListRnCommand;
import seedu.address.logic.commands.ListRtCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for parsing arguments for list to see if command is valid.
 * List commands should have no arguments.
 */
public class ListCommandParser {

    public static final String MESSAGE_INVALID_FORMAT = "Please exclude any arguments!";

    /**
     * Parses arguments for the ListCommand and validates there are no arguments.
     * @param arguments the arguments provided with the command
     * @return a new ListCommand
     * @throws ParseException if arguments are found
     */
    public ListCommand parseForList(String arguments) throws ParseException {
        if (arguments.length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FORMAT));
        }
        return new ListCommand();
    }

    /**
     * Parses arguments for the ListRtCommand and validates there are no arguments.
     * @param arguments the arguments provided with the command
     * @return a new ListRtCommand
     * @throws ParseException if arguments are found
     */
    public ListRtCommand parseForListRT(String arguments) throws ParseException {
        if (arguments.length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FORMAT));
        }
        return new ListRtCommand();
    }

    /**
     * Parses arguments for the ListRnCommand and validates there are no arguments.
     * @param arguments the arguments provided with the command
     * @return a new ListRnCommand
     * @throws ParseException if arguments are found
     */
    public ListRnCommand parseForListRN(String arguments) throws ParseException {
        if (arguments.length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FORMAT));
        }
        return new ListRnCommand();
    }

    /**
     * Parses arguments for the ListRCommand and validates there are no arguments.
     * @param arguments the arguments provided with the command
     * @return a new ListRCommand
     * @throws ParseException if arguments are found
     */
    public ListRCommand parseForListR(String arguments) throws ParseException {
        if (!arguments.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FORMAT));
        }
        return new ListRCommand();
    }

    /**
     * Parses arguments for the ListPCommand and validates there are no arguments.
     * @param arguments the arguments provided with the command
     * @return a new ListPCommand
     * @throws ParseException if arguments are found
     */
    public ListPCommand parseForListP(String arguments) throws ParseException {
        if (arguments.length() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_FORMAT));
        }
        return new ListPCommand();
    }
}
