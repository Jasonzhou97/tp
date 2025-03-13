package seedu.address.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.reservation.Reservation;

/**
 * Provides utility methods for filtering reservations by date.
 */
public class ReservationsFilter {

    /**
     * Returns a predicate that filters reservations scheduled for today.
     *
     * @param reservationsList The list of reservations to filter
     * @return Predicate for today's reservations
     */
    public static Predicate<Reservation> filterForToday(FilteredList<Reservation> reservationsList) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayDate = today.format(formatter);
        //filter by today
        Predicate<Reservation> todayPredicate = reservation -> reservation.getDate().value.equals(todayDate);
        return todayPredicate;
    }

    /**
     * Returns a predicate that filters reservations scheduled for tomorrow.
     *
     * @param reservationsList The list of reservations to filter
     * @return Predicate for tomorrow's reservations
     */
    public static Predicate<Reservation> filterForTomorrow(FilteredList<Reservation> reservationsList) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tomorrowDate = tomorrow.format(formatter);
        //filter by tomorrow
        Predicate<Reservation> tomorrowPredicate = reservation -> reservation.getDate().value.equals(tomorrowDate);
        return tomorrowPredicate;
    }
}
