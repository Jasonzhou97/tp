package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyGastroBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GastroBookStorage gastroBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(GastroBookStorage gastroBookStorage, UserPrefsStorage userPrefsStorage) {
        this.gastroBookStorage = gastroBookStorage;
        this.userPrefsStorage = userPrefsStorage;
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
        return gastroBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGastroBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(gastroBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGastroBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return gastroBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyGastroBook addressBook) throws IOException {
        saveAddressBook(addressBook, gastroBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyGastroBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        gastroBookStorage.saveAddressBook(addressBook, filePath);
    }

}
