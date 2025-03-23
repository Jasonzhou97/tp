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
import seedu.address.model.ReadOnlyPersonsList;

/**
 * A class to access PersonsList data stored as a json file on the hard disk.
 */
public class JsonPersonsListStorage implements PersonsListStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPersonsListStorage.class);

    private Path filePath;

    public JsonPersonsListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPersonsListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPersonsList> readPersonsList() throws DataLoadingException {
        return readPersonsList(filePath);
    }

    /**
     * Similar to {@link #readPersonsList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPersonsList> readPersonsList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        Optional<JsonSerializablePersonsList> jsonPersonsList = JsonUtil.readJsonFile(
                filePath, JsonSerializablePersonsList.class);

        if (!jsonPersonsList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPersonsList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePersonsList(ReadOnlyPersonsList personsList) throws IOException {
        savePersonsList(personsList, filePath);  // This uses the instance variable filePath
    }

    /**
     * Similar to {@link #savePersonsList(ReadOnlyPersonsList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePersonsList(ReadOnlyPersonsList personsList, Path filePath) throws IOException {
        requireNonNull(personsList);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePersonsList(personsList), filePath);
    }


}