package seedu.address.storage;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPersonsList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.PersonsList}.
 */
public interface PersonsListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getPersonsListFilePath();

    /**
     * Returns PersonsList data as a {@link ReadOnlyPersonsList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPersonsList> readPersonsList() throws DataLoadingException;

    /**
     * @see #getPersonsListFilePath()
     */
    Optional<ReadOnlyPersonsList> readPersonsList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPersonsList} to the storage.
     *
     * @param personsList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePersonsList(ReadOnlyPersonsList personsList) throws IOException;

    /**
     * @see #savePersonsList(ReadOnlyPersonsList)
     */
    void savePersonsList(ReadOnlyPersonsList personsList, Path filePath) throws IOException;
}