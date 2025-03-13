package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

public class ListRNCommand extends Command{
    public static final String COMMAND_WORD = "listrn";
    public static final String MESSAGE_SUCCESS = "Listed all reservations for tomorrow";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterReservationsForTomorrow(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
