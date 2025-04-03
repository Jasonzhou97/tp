package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;

public class PersonsListTest {

    private PersonsList personsList;

    @BeforeEach
    public void setUp() {
        personsList = new PersonsList();
    }


    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personsList.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInList_returnsFalse() {
        Person testPerson = new Person(new Name("Alice"), new Phone("91234007"));
        assertFalse(personsList.hasPerson(testPerson));
    }

    @Test
    public void hasPerson_personInList_returnsTrue() {
        Person testPerson = new Person(new Name("Alice"), new Phone("91234567"));
        personsList.addPerson(testPerson);
        assertTrue(personsList.hasPerson(testPerson));
    }

    @Test
    public void getPerson_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonsList.getPerson(null));
    }


    @Test
    public void getPerson_personInList_returnsPerson() {
        Person testPerson = new Person(new Name("Alice"), new Phone("91234567"));
        personsList.addPerson(testPerson);

        Person retrievedPerson = PersonsList.getPerson(new Phone("91234567"));
        assertNotNull(retrievedPerson);
        assertEquals("Alice", retrievedPerson.getName().getFullName());
    }

    @Test
    public void recordBooking_thresholdReached_marksAsRegular() {
        Name name = new Name("David");
        Phone phone = new Phone("76543210");

        // Record multiple bookings to reach the threshold
        Person resultPerson = null;
        for (int i = 0; i < PersonsList.REGULAR_CUSTOMER_THRESHOLD; i++) {
            resultPerson = personsList.recordBooking(name, phone);
        }

        assertEquals(PersonsList.REGULAR_CUSTOMER_THRESHOLD, resultPerson.getCounter());
        assertTrue(resultPerson.isRegular());
    }

    @Test
    public void getRegularCustomers_hasRegulars_returnsListOfRegulars() {
        // Add a regular customer
        Name regularName = new Name("Frank");
        Phone regularPhone = new Phone("54321098");
        for (int i = 0; i < PersonsList.REGULAR_CUSTOMER_THRESHOLD; i++) {
            personsList.recordBooking(regularName, regularPhone);
        }

        // Add a non-regular customer
        personsList.recordBooking(new Name("Grace"), new Phone("43210987"));

        ArrayList<Person> regulars = personsList.getRegularCustomers();
        assertEquals(1, regulars.size());
        assertEquals("Frank", regulars.get(0).getName().getFullName());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(personsList.equals(personsList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(personsList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(personsList.equals("different type"));
    }

    @Test
    public void equals_samePeople_returnsTrue() {
        PersonsList otherList = new PersonsList();

        Person person = new Person(new Name("Ivan"), new Phone("21098765"));
        personsList.addPerson(person);
        otherList.addPerson(person);

        assertTrue(personsList.equals(otherList));
    }

}
