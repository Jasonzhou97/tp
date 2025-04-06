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
 * Manages storage of GastroBook data in local storage.
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


    // ================ GatroBook methods ==============================

    @Override
    public Path getGastroBookFilePath() {
        return gastroBookStorage.getGastroBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGastroBook> readGastroBook() throws DataLoadingException {
        return readGastroBook(gastroBookStorage.getGastroBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGastroBook> readGastroBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return gastroBookStorage.readGastroBook(filePath);
    }

    @Override
    public void saveGastroBook(ReadOnlyGastroBook gastroBook) throws IOException {
        saveGastroBook(gastroBook, gastroBookStorage.getGastroBookFilePath());
    }

    @Override
    public void saveGastroBook(ReadOnlyGastroBook gastroBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        gastroBookStorage.saveGastroBook(gastroBook, filePath);
    }

}
