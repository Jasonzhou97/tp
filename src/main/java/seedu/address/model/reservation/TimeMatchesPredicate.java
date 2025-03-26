package seedu.address.model.reservation;

import java.time.LocalTime;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a reservation is ongoing at a specified time.
 * A reservation is considered ongoing if its start time is at or before the search time,
 * and its end time (start time + duration) is after the search time.
 */
public class TimeMatchesPredicate implements Predicate<Reservation> {
    private final LocalTime searchTime;

    public TimeMatchesPredicate(LocalTime searchTime) {
        this.searchTime = searchTime;
    }

    @Override
    public boolean test(Reservation reservation) {
        LocalTime startTime = reservation.getTime().toLocalTime();
        int durationMinutes = reservation.getDuration().toMinutes();
        LocalTime endTime = startTime.plusMinutes(durationMinutes);

        // The reservation is ongoing at searchTime if startTime <= searchTime < endTime.
        return !startTime.isAfter(searchTime) && searchTime.isBefore(endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TimeMatchesPredicate)) {
            return false;
        }
        TimeMatchesPredicate otherPredicate = (TimeMatchesPredicate) other;
        return searchTime.equals(otherPredicate.searchTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("searchTime", searchTime).toString();
    }
}
