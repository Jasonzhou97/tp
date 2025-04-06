package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyGastroBook;

/**
 * A class to access GastroBook data stored as a json file on the hard disk.
 */
public class JsonGastroBookStorage implements GastroBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGastroBookStorage.class);

    private Path filePath;

    public JsonGastroBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGastroBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGastroBook> readGastroBook() throws DataLoadingException {
        return readGastroBook(filePath);
    }

    /**
     * Similar to {@link #readGastroBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyGastroBook> readGastroBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableGastroBook> jsonGastroBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableGastroBook.class);
        if (!jsonGastroBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGastroBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveGastroBook(ReadOnlyGastroBook gastroBook) throws IOException {
        saveGastroBook(gastroBook, filePath);
    }

    /**
     * Similar to {@link #saveGastroBook(ReadOnlyGastroBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGastroBook(ReadOnlyGastroBook gastroBook, Path filePath) throws IOException {
        requireNonNull(gastroBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGastroBook(gastroBook), filePath);
    }

}
