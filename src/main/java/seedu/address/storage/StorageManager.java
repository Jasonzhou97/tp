package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPersonsList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook and PersonsList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private PersonsListStorage personsListStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage}, {@code UserPrefsStorage}
     * and {@code PersonsListStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          PersonsListStorage personsListStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.personsListStorage = personsListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return addressBookStorage.readAddressBook();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ PersonsList methods ==============================

    @Override
    public Path getPersonsListFilePath() {
        return personsListStorage.getPersonsListFilePath();
    }

    @Override
    public Optional<ReadOnlyPersonsList> readPersonsList() throws DataLoadingException {
        return personsListStorage.readPersonsList();
    }

    @Override
    public Optional<ReadOnlyPersonsList> readPersonsList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read persons list data from file: " + filePath);
        return personsListStorage.readPersonsList(filePath);
    }

    @Override
    public void savePersonsList(ReadOnlyPersonsList personsList) throws IOException {
        savePersonsList(personsList, personsListStorage.getPersonsListFilePath());
    }

    @Override
    public void savePersonsList(ReadOnlyPersonsList personsList, Path filePath) throws IOException {
        logger.fine("Attempting to write persons list to data file: " + filePath);
        personsListStorage.savePersonsList(personsList, filePath);
    }
}
