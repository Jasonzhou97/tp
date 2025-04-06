package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReservations.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;

/**
 * Unit tests for {@link ModelManager}.
 */
public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new GastroBook(), new GastroBook(modelManager.getAddressBook()));
        assertEquals(new PersonsList(), modelManager.getPersonsList());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_userPrefsSet() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_success() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_success() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReservation(null));
    }

    @Test
    public void hasReservation_reservationNotInGastroBook_returnsFalse() {
        assertFalse(modelManager.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationInGastroBook_returnsTrue() {
        modelManager.addReservation(ALICE);
        assertTrue(modelManager.hasReservation(ALICE));
    }

    @Test
    public void getFilteredReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredReservationList().remove(0));
    }

    @Test
    public void getOverallReservationList_success() {
        modelManager.addReservation(ALICE);
        // Apply a filter first
        modelManager.updateFilteredReservationList(r -> r.getName().equals(ALICE.getName()));
        assertEquals(1, modelManager.getFilteredReservationList().size());

    }
    @Test
    public void addPerson_personAdded_success() {
        Person person = new Person(new Name("Test Person"), new Phone("12345678"));
        modelManager.addPerson(person);
        assertTrue(modelManager.hasPerson(person));
    }

    @Test
    public void recordBooking_personExists_updatesCounter() {
        // Add a person first
        Name name = new Name("Test Person");
        Phone phone = new Phone("12345678");
        Person person = new Person(name, phone);
        modelManager.addPerson(person);

        // Record booking for the same person
        Person updatedPerson = modelManager.recordBooking(name, phone);

        // Counter should be incremented
        assertEquals(1, updatedPerson.getCounter());
    }

    @Test
    public void addReservation_updatesPersonsList() {
        // Add a reservation
        modelManager.addReservation(ALICE);

        // PersonsList should be updated with the customer info
        assertTrue(modelManager.hasPerson(
                new Person(ALICE.getName(), ALICE.getPhone())));
    }

}
