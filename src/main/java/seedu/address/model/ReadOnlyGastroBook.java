package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.reservation.Reservation;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyGastroBook {

    /**
     * Returns an unmodifiable view of the reservations list.
     * This list will not contain any duplicate reservations.
     */
    ObservableList<Reservation> getReservationList();


}
