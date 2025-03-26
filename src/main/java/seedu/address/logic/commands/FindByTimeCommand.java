package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;

/**
 * Finds and lists all reservations scheduled for today that are ongoing at the specified time.
 * A reservation qualifies if its start time is at or before the search time and its end time (computed from its start time and duration)
 * is strictly after the search time. Reservations ending exactly at the search time are not included.
 */
public class FindByTimeCommand extends Command {

    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all reservations scheduled for today that are ongoing at the specified time.\n"
            + "A reservation is ongoing if its start time is at or before the specified time and "
            + "its end time is after the specified time.\n"
            + "Parameters: TIME (in HHmm format)\n"
            + "Example: " + COMMAND_WORD + " 1400";

    private final LocalTime searchTime;

    public FindByTimeCommand(LocalTime searchTime) {
        this.searchTime = searchTime;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredReservationList(createTimePredicate(searchTime));
        return new CommandResult(String.format(Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
                model.getFilteredReservationList().size()));
    }

    /**
     * Creates a predicate that checks whether a reservation is scheduled for today and is ongoing at the specified time.
     */
    private Predicate<Reservation> createTimePredicate(LocalTime searchTime) {
        return reservation -> {
            // Only consider reservations for today
            LocalDate today = LocalDate.now();
            LocalDate reservationDate = reservation.getDate().toLocalDate(); // Assumes getDate() returns a StartDate.
            if (!reservationDate.equals(today)) {
                return false;
            }

            // Get the reservation's start time as a LocalTime.
            LocalTime startTime = reservation.getStartTime().toLocalTime(); // Assumes getStartTime() returns a StartTime.

            // Compute the end time. Here we assume that getDuration() returns a Duration object,
            // and that you have a method to convert that duration to minutes.
            int durationMinutes = reservation.getDuration().toMinutes();
            LocalTime endTime = startTime.plusMinutes(durationMinutes);

            // The reservation is ongoing at searchTime if:
            // startTime <= searchTime < endTime.
            return !startTime.isAfter(searchTime) && searchTime.isBefore(endTime);
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindByTimeCommand)) {
            return false;
        }
        FindByTimeCommand otherCommand = (FindByTimeCommand) other;
        return searchTime.equals(otherCommand.searchTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("searchTime", searchTime.format(DateTimeFormatter.ofPattern("HHmm")))
                .toString();
    }
}
