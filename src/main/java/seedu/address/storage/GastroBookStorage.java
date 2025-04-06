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
    Path getGastroBookFilePath();

    /**
     * Returns GastroBook data as a {@link ReadOnlyGastroBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyGastroBook> readGastroBook() throws DataLoadingException;

    /**
     * @see #getGastroBookFilePath()
     */
    Optional<ReadOnlyGastroBook> readGastroBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyGastroBook} to the storage.
     * @param gastroBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGastroBook(ReadOnlyGastroBook gastroBook) throws IOException;

    /**
     * @see #saveGastroBook(ReadOnlyGastroBook)
     */
    void saveGastroBook(ReadOnlyGastroBook gastroBook, Path filePath) throws IOException;

}
