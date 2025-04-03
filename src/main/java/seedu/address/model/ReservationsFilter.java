package seedu.address.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.reservation.Reservation;

/**
 * Provides utility methods for filtering reservations by date.
 */
public class ReservationsFilter {

    private static PersonsList personsList = new PersonsList();

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


    /**
     * Returns a predicate that filters reservations scheduled for tomorrow or today.
     *
     *
     * @return Predicate for tomorrow or today reservations
     */
    public static Predicate<Reservation> filterForTodayOrTomorrowPredicate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayDate = today.format(formatter);
        String tomorrowDate = tomorrow.format(formatter);

        // Predicate to check if the reservation is either today or tomorrow
        Predicate<Reservation> todayOrTomorrowPredicate = reservation ->
                reservation.getDate().value.equals(todayDate) || reservation.getDate().value.equals(tomorrowDate);

        return todayOrTomorrowPredicate;
    }
    /**
     * Returns a predicate that filters reservations previously.
     *
     *
     * @return Predicate for tomorrow or today reservations
     */
    public static Predicate<Reservation> filterForPrevious() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayDate = today.format(formatter);
        String tomorrowDate = tomorrow.format(formatter);

        // Predicate to check if the reservation is before today
        Predicate<Reservation> previousPredicate = reservation ->
                !(reservation.getDate().value.equals(todayDate) || reservation.getDate().value.equals(tomorrowDate));

        return previousPredicate;
    }
    /**
     * Returns a predicate that filters reservations made by regulars.
     *
     * @param personsList The list of reservations to filter
     * @return Predicate for regulars' reservation
     */
    public static Predicate<Reservation> filterByRegular(PersonsList personsList) {
        // Load the current persons list
        personsList.loadListFromFile();

        // get a list of regular numbers
        Set<String> regularNumbers = personsList.getRegularCustomers()
                .stream()
                .map(person -> person.getPhone().value)
                .collect(Collectors.toSet());

        // check if any reservations match these regular customers
        Predicate<Reservation> regularPredicate = reservation -> {
            String reservationPhone = reservation.getPhone().value;
            return regularNumbers.contains(reservationPhone);
        };

        return regularPredicate;
    }

}
