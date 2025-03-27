package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Reservation> PREDICATE_SHOW_ALL_RESERVATIONS = ReservationsFilter.filterForTodayOrTomorrowPredicate();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the address book.
     */
    boolean hasReservation(Reservation reservation);

    /**
     * Deletes the given reservation.
     * The reservation must exist in the address book.
     */
    void deleteReservation(Reservation target);
    /**
     * Updates person details in PersonsList when a reservation is edited.
     * @param oldReservation the reservation before editing
     * @param newReservation the reservation after editing
     */
    void updatePersonsListAfterEdit(Reservation oldReservation, Reservation newReservation);
    /**
     * Adds the given reservation.
     * {@code reservation} must not already exist in the address book.
     */
    void addReservation(Reservation reservation);

    /**
     * Replaces the given reservation {@code target} with {@code editedReservation}.
     * {@code target} must exist in the address book.
     * The reservation identity of {@code editedReservation} must not be the same as another existing reservation
     * in the address book.
     */
    void setReservation(Reservation target, Reservation editedReservation);

    /**
     * Returns an unmodifiable view of the filtered reservation list
     */
    ObservableList<Reservation> getFilteredReservationList();

    /**
     * Returns an unmodifiable view of all reservations
     */
    ObservableList<Reservation> getOverallReservationList();

    /**
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    /**
     * Filters reservations for today.
     */
    void filterReservationsForToday(Predicate<Reservation> predicate);

    /**
     * Filters reservations for tomorrow.
     */
    void filterReservationsForTomorrow(Predicate<Reservation> predicate);

    // New methods for PersonsList

    /**
     * Returns true if a person with the same identity as {@code person} exists in the persons list.
     */
    boolean hasPerson(Person person);

    /**
     * Adds a person to the persons list.
     * The person must not already exist in the persons list.
     */
    void addPerson(Person person);

    /**
     * Records a booking for the specified person.
     * If the person doesn't exist, creates a new person.
     * Updates booking count and regular status.
     * @param name Name of the person
     * @param phone Phone number of the person
     * @return The updated or newly created Person
     */
    Person recordBooking(Name name, Phone phone);

    /**
     * Returns a list of all regular customers.
     */
    ArrayList<Person> getRegularCustomers();

    /**
     * Returns the list of all persons.
     */
    PersonsList getPersonsList();
}