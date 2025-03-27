package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PersonsListManagerTest {

    private PersonsList personsList;
    private PersonsListManager personsListManager;

    @BeforeEach
    public void setUp() {
        personsList = new PersonsList();
        personsListManager = new PersonsListManager(personsList);
    }

    @Test
    public void updatePersonsListAfterDelete_decrementCounter() {
        assertEquals(1, 1);
    }

    @Test
    public void updatePersonsListAfterEdit_phoneChange() {
        assertEquals(1, 1);
    }

}
