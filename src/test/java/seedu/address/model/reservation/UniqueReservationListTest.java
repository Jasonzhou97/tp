package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationNotFoundException;
import seedu.address.testutil.ReservationBuilder;

public class UniqueReservationListTest {

    private UniqueReservationList uniqueReservationList;
    private Reservation alice;
    private Reservation bob;

    @BeforeEach
    public void setUp() {
        uniqueReservationList = new UniqueReservationList();
        alice = new ReservationBuilder().withName("Alice").withPhone("12345678").build();
        bob = new ReservationBuilder().withName("Bob").withPhone("87654321").build();
    }

    @Test
    public void contains_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.contains(null));
    }

    @Test
    public void contains_reservationNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(alice));
    }

    @Test
    public void contains_reservationInList_returnsTrue() {
        uniqueReservationList.add(alice);
        assertTrue(uniqueReservationList.contains(alice));
    }

    @Test
    public void contains_reservationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReservationList.add(alice);
        Reservation editedAlice = new ReservationBuilder(alice).withTags("friend").build();
        assertTrue(uniqueReservationList.contains(editedAlice));
    }

    @Test
    public void add_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.add(null));
    }

    @Test
    public void add_duplicateReservation_throwsDuplicateReservationException() {
        uniqueReservationList.add(alice);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.add(alice));
    }

    @Test
    public void setReservation_nullTargetReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(null, alice));
    }

    @Test
    public void setReservation_nullEditedReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList
                .setReservation(alice, null));
    }

    @Test
    public void setReservation_targetReservationNotInList_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.setReservation(alice, alice));
    }

    @Test
    public void setReservation_editedReservationIsSameReservation_success() {
        uniqueReservationList.add(alice);
        uniqueReservationList.setReservation(alice, alice);
        UniqueReservationList expected = new UniqueReservationList();
        expected.add(alice);
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasSameIdentity_success() {
        uniqueReservationList.add(alice);
        String validTagHusband = "husband";
        Reservation editedAlice = new ReservationBuilder(alice).withTags(validTagHusband).build();
        uniqueReservationList.setReservation(alice, editedAlice);
        UniqueReservationList expected = new UniqueReservationList();
        expected.add(editedAlice);
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasDifferentIdentity_success() {
        uniqueReservationList.add(alice);
        uniqueReservationList.setReservation(alice, bob);
        UniqueReservationList expected = new UniqueReservationList();
        expected.add(bob);
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasNonUniqueIdentity_throwsDuplicateReservationException() {
        uniqueReservationList.add(alice);
        uniqueReservationList.add(bob);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.setReservation(alice, bob));
    }

    @Test
    public void remove_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.remove(null));
    }

    @Test
    public void remove_reservationDoesNotExist_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.remove(alice));
    }

    @Test
    public void remove_existingReservation_removesReservation() {
        uniqueReservationList.add(alice);
        uniqueReservationList.remove(alice);
        UniqueReservationList expected = new UniqueReservationList();
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservations_nullUniqueReservationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList
                .setReservations((UniqueReservationList) null));
    }

    @Test
    public void setReservations_uniqueReservationList_replacesOwnListWithProvidedUniqueReservationList() {
        uniqueReservationList.add(alice);
        UniqueReservationList expected = new UniqueReservationList();
        expected.add(bob);
        uniqueReservationList.setReservations(expected);
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservations_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservations((List<Reservation>) null));
    }

    @Test
    public void setReservations_list_replacesOwnListWithProvidedList() {
        uniqueReservationList.add(alice);
        List<Reservation> reservationList = Collections.singletonList(bob);
        uniqueReservationList.setReservations(reservationList);
        UniqueReservationList expected = new UniqueReservationList();
        expected.add(bob);
        assertEquals(expected, uniqueReservationList);
    }

    @Test
    public void setReservations_listWithDuplicateReservations_throwsDuplicateReservationException() {
        List<Reservation> listWithDuplicates = Arrays.asList(alice, alice);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList
                .setReservations(listWithDuplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueReservationList.add(alice);
        assertThrows(UnsupportedOperationException.class, () -> {
            uniqueReservationList.asUnmodifiableObservableList().remove(0);
        });
    }

    @Test
    public void toStringMethod_returnsCorrectString() {
        uniqueReservationList.add(alice);
        assertEquals(uniqueReservationList.asUnmodifiableObservableList().toString(), uniqueReservationList.toString());
    }
}
