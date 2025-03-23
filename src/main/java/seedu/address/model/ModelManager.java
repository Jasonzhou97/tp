package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Person;
import seedu.address.model.reservation.Reservation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final PersonsList personsList;
    private final UserPrefs userPrefs;
    private final FilteredList<Reservation> filteredReservations;

    // Instead of a final FilteredList, we'll use Observable and Filtered separately
    private final ObservableList<Person> observablePersonsList;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook, personsList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyPersonsList personsList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, personsList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", persons list: " + personsList
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.personsList = new PersonsList(personsList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredReservations = new FilteredList<>(this.addressBook.getReservationList());

        // Create the observable list from the ArrayList
        observablePersonsList = FXCollections.observableArrayList(this.personsList.getPersonsList());
        filteredPersons = new FilteredList<>(observablePersonsList);
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs, and an empty personsList.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new PersonsList(), userPrefs);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getPersonsListFilePath() {
        return userPrefs.getPersonsListFilePath();
    }

    @Override
    public void setPersonsListFilePath(Path personsListFilePath) {
        requireNonNull(personsListFilePath);
        userPrefs.setPersonsListFilePath(personsListFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return addressBook.hasReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation target) {
        addressBook.removeReservation(target);
    }

    @Override
    public void addReservation(Reservation reservation) {
        addressBook.addReservation(reservation);
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
    }

    @Override
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);
        addressBook.setReservation(target, editedReservation);
    }

    //=========== PersonsList ================================================================================

    @Override
    public void setPersonsList(ReadOnlyPersonsList personsList) {
        this.personsList.resetData(personsList);
        // Update the observable list with the new data
        updateObservablePersonsList();
    }

    @Override
    public ReadOnlyPersonsList getPersonsList() {
        return personsList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personsList.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        personsList.removePerson(target);
        // Update the observable list
        updateObservablePersonsList();
    }

    @Override
    public void addPerson(Person person) {
        personsList.addPerson(person);
        // Update the observable list
        updateObservablePersonsList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        personsList.setPerson(target, editedPerson);
        // Update the observable list
        updateObservablePersonsList();
    }

    /**
     * Updates the observable list with the current state of the persons list.
     */
    private void updateObservablePersonsList() {
        observablePersonsList.clear();
        observablePersonsList.addAll(personsList.getPersonsList());
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return filteredReservations;
    }

    @Override
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code personsList}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }
    @Override
    public ObservableList<Reservation> getOverallReservationList() {
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return filteredReservations;
    }
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    @Override
    public void filterReservationsForToday(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        Predicate<Reservation> todayPredicate = ReservationsFilter.filterForToday(filteredReservations);
        filteredReservations.setPredicate(todayPredicate);
    }
    @Override
    public void filterReservationsForTomorrow(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        Predicate<Reservation> tomorrowPredicate = ReservationsFilter.filterForTomorrow(filteredReservations);
        filteredReservations.setPredicate(tomorrowPredicate);
    }

    @Override
    public boolean equals(Object obj) {
        // same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && personsList.equals(other.personsList)
                && userPrefs.equals(other.userPrefs)
                && filteredReservations.equals(other.filteredReservations)
                && filteredPersons.equals(other.filteredPersons);
    }
}