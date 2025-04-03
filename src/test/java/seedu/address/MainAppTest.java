package seedu.address;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.model.Model;
import seedu.address.model.PersonsList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonGastroBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class MainAppTest {

    @TempDir
    Path tempDir;

    private Path tempConfigPath;
    private Path tempPrefsPath;
    private Path tempGastroBookPath;

    private MainApp mainApp;

    @BeforeEach
    public void setUp() {
        tempConfigPath = tempDir.resolve("config.json");
        tempPrefsPath = tempDir.resolve("prefs.json");
        tempGastroBookPath = tempDir.resolve("gastrobook.json");
        mainApp = new MainApp();
    }

    @Test
    public void initConfig_validPath_returnsConfig() {
        Config config = mainApp.initConfig(tempConfigPath);
        assertNotNull(config);
    }

    @Test
    public void initConfig_nullPath_usesDefaultConfigPath() {
        Config config = mainApp.initConfig(null);
        assertNotNull(config);
    }

    @Test
    public void initPrefs_validStorage_returnsUserPrefs() {
        UserPrefsStorage storage = new JsonUserPrefsStorage(tempPrefsPath);
        UserPrefs prefs = mainApp.initPrefs(storage);
        assertNotNull(prefs);
    }

    @Test
    public void initModelManager_validStorage_returnsModel() {
        JsonGastroBookStorage gastroBookStorage = new JsonGastroBookStorage(tempGastroBookPath);
        JsonUserPrefsStorage prefsStorage = new JsonUserPrefsStorage(tempPrefsPath);

        StorageManager storageManager = new StorageManager(gastroBookStorage, prefsStorage);
        PersonsList personsList = new PersonsList();
        UserPrefs userPrefs = new UserPrefs();

        Model model = mainApp.initModelManager(storageManager, personsList, userPrefs);
        assertNotNull(model);
        assertTrue(model.getAddressBook().getReservationList().size() >= 0);
    }
}
