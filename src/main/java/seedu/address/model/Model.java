package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Person;
import seedu.address.model.reservation.Reservation;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Reservation> PREDICATE_SHOW_ALL_RESERVATIONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' persons list file path.
     */
    Path getPersonsListFilePath();

    /**
     * Sets the user prefs' persons list file path.
     */
    void setPersonsListFilePath(Path personsListFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces persons list data with the data in {@code personsList}.
     */
    void setPersonsList(ReadOnlyPersonsList personsList);

    /**
     * Returns the PersonsList
     */
    ReadOnlyPersonsList getPersonsList();

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
     * Returns true if a person with the same identity as {@code person} exists in the persons list.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the persons list.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the persons list.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the persons list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the persons list.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered reservation list
     */
    ObservableList<Reservation> getFilteredReservationList();
    ObservableList<Reservation> getOverallReservationList();
    void filterReservationsForToday(Predicate<Reservation> predicate);

    void filterReservationsForTomorrow(Predicate<Reservation> predicate);

    /**
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    /**
     * Returns an unmodifiable view of the filtered persons list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered persons list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}