package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReservations.ALICE;
import static seedu.address.testutil.TypicalReservations.BENSON;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationNotFoundException;
import seedu.address.testutil.ReservationBuilder;

public class GastroBookTest {

    private final GastroBook gastroBook = new GastroBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), gastroBook.getReservationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gastroBook.resetData(null));
    }

    @Test
    public void resetData_withDuplicateReservations_throwsDuplicateReservationException() {
        // Two reservations with the same identity fields
        Reservation editedAlice = new ReservationBuilder(ALICE).build();
        List<Reservation> newReservations = Arrays.asList(ALICE, editedAlice);
        GastroBookStub newData = new GastroBookStub(newReservations);

        assertThrows(DuplicateReservationException.class, () -> gastroBook.resetData(newData));
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gastroBook.hasReservation(null));
    }

    @Test
    public void hasReservation_reservationNotInGastroBook_returnsFalse() {
        assertFalse(gastroBook.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationInGastroBook_returnsTrue() {
        gastroBook.addReservation(ALICE);
        assertTrue(gastroBook.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationWithSameIdentityFieldsInGastroBook_returnsTrue() {
        gastroBook.addReservation(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE).build();
        assertTrue(gastroBook.hasReservation(editedAlice));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> gastroBook.getReservationList().remove(0));
    }

    @Test
    public void addReservation_validReservation_success() {
        gastroBook.addReservation(ALICE);
        assertTrue(gastroBook.hasReservation(ALICE));
        assertEquals(1, gastroBook.getReservationList().size());
    }

    @Test
    public void addReservation_duplicateReservation_throwsDuplicateReservationException() {
        gastroBook.addReservation(ALICE);
        assertThrows(DuplicateReservationException.class, () -> gastroBook.addReservation(ALICE));
    }

    @Test
    public void setReservation_targetNotInGastroBook_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () ->
                gastroBook.setReservation(ALICE, ALICE));
    }

    @Test
    public void setReservation_validTargetAndEditedReservation_success() {
        gastroBook.addReservation(ALICE);
        gastroBook.setReservation(ALICE, BENSON);

        assertFalse(gastroBook.hasReservation(ALICE));
        assertTrue(gastroBook.hasReservation(BENSON));
    }

    @Test
    public void setReservation_editedReservationIsDuplicate_throwsDuplicateReservationException() {
        gastroBook.addReservation(ALICE);
        gastroBook.addReservation(BENSON);

        assertThrows(DuplicateReservationException.class, () ->
                gastroBook.setReservation(ALICE, BENSON));
    }

    @Test
    public void removeReservation_reservationInGastroBook_success() {
        gastroBook.addReservation(ALICE);
        gastroBook.removeReservation(ALICE);

        assertFalse(gastroBook.hasReservation(ALICE));
        assertEquals(0, gastroBook.getReservationList().size());
    }

    @Test
    public void removeReservation_reservationNotInGastroBook_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> gastroBook.removeReservation(ALICE));
    }

    @Test
    public void toString_nonEmptyGastroBook_returnsStringRepresentation() {
        gastroBook.addReservation(ALICE);
        String result = gastroBook.toString();

        assertTrue(result.contains("persons"));
    }

    @Test
    public void equals_sameGastroBook_returnsTrue() {
        GastroBook copy = new GastroBook();
        copy.addReservation(ALICE);
        gastroBook.addReservation(ALICE);

        assertTrue(gastroBook.equals(copy));
    }

    @Test
    public void equals_differentGastroBook_returnsFalse() {
        GastroBook other = new GastroBook();
        other.addReservation(BENSON);
        gastroBook.addReservation(ALICE);

        assertFalse(gastroBook.equals(other));
    }

    @Test
    public void hashCode_sameContent_sameHashCode() {
        GastroBook copy = new GastroBook();
        copy.addReservation(ALICE);
        gastroBook.addReservation(ALICE);

        assertEquals(gastroBook.hashCode(), copy.hashCode());
    }

    /**
     * A stub ReadOnlyGastroBook whose reservations list can violate interface constraints.
     */
    private static class GastroBookStub implements ReadOnlyGastroBook {
        private final ObservableList<Reservation> reservations;

        GastroBookStub(Collection<Reservation> reservations) {
            this.reservations = FXCollections.observableArrayList(reservations);
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }
    }
}