package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.GastroBook;
import seedu.address.model.ReadOnlyGastroBook;

/**
 * Represents a storage for {@link GastroBook}.
 */
public interface GastroBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyGastroBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyGastroBook> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyGastroBook> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyGastroBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyGastroBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyGastroBook)
     */
    void saveAddressBook(ReadOnlyGastroBook addressBook, Path filePath) throws IOException;

}
