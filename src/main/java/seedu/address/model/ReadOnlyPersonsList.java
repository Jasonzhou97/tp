package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.reservation.Reservation;

import java.util.ArrayList;

public interface ReadOnlyPersonsList {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ArrayList<Person> getPersonsList();
}
