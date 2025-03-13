package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.reservation.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class ReservationsFilter {


    public static Predicate<Reservation> filterForToday(FilteredList<Reservation> reservationsList) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayDate = today.format(formatter);

        //filter by today
        Predicate<Reservation> todayPredicate = reservation ->
                reservation.getDate().value.equals(todayDate);
        return todayPredicate;

    }
    public static Predicate<Reservation> filterForTomorrow(FilteredList<Reservation> reservationsList) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String tomorrowDate = tomorrow.format(formatter);

        //filter by tomorrow
        Predicate<Reservation> tomorrowPredicate = reservation ->
                reservation.getDate().value.equals(tomorrowDate);
        return tomorrowPredicate;

    }
}

