package seedu.address.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;

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
        // Setup
        Name name = new Name("Test Name");
        Phone phone = new Phone("12345678");
        Person person = new Person(name, phone);
        person.setCounter(2);
        personsList.addPerson(person);

        Reservation reservation = new Reservation(name, phone, null, null, null, null, null, null, null, null, false);

        // Execute
        personsListManager.updatePersonsListAfterDelete(reservation);

        // Verify
        Person result = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(phone.value)) {
                result = p;
                break;
            }
        }

        assertNotNull(result);
        assertEquals(1, result.getCounter());
    }

    @Test
    public void updatePersonsListAfterDelete_removePerson() {
        // Setup
        Name name = new Name("Test Name");
        Phone phone = new Phone("12345678");
        Person person = new Person(name, phone);
        person.setCounter(1);
        personsList.addPerson(person);

        Reservation reservation = new Reservation(name, phone, null, null, null, null, null, null, null, null, false);

        // Execute
        personsListManager.updatePersonsListAfterDelete(reservation);

        // Verify
        Person result = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(phone.value)) {
                result = p;
                break;
            }
        }

        assertNull(result);
    }

    @Test
    public void updatePersonsListAfterEdit_nameChangeOnly() {
        // Setup
        Name oldName = new Name("Old Name");
        Name newName = new Name("New Name");
        Phone phone = new Phone("12345678");

        Person person = new Person(oldName, phone);
        person.setCounter(2);
        personsList.addPerson(person);

        Reservation oldReservation = new Reservation(oldName, phone, null, null, null, null, null, null, null, null, false);
        Reservation newReservation = new Reservation(newName, phone, null, null, null, null, null, null, null, null, false);

        // Execute
        personsListManager.updatePersonsListAfterEdit(oldReservation, newReservation);

        // Verify
        Person result = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(phone.value)) {
                result = p;
                break;
            }
        }

        assertNotNull(result);
        assertEquals(newName.getFullName(), result.getName().getFullName());
        assertEquals(2, result.getCounter());
    }

    @Test
    public void updatePersonsListAfterEdit_phoneChange() {
        // Setup
        Name name = new Name("Test Name");
        Phone oldPhone = new Phone("12345678");
        Phone newPhone = new Phone("87654321");

        Person person = new Person(name, oldPhone);
        person.setCounter(2);
        personsList.addPerson(person);

        Reservation oldReservation = new Reservation(name, oldPhone, null, null, null, null, null, null, null, null, false);
        Reservation newReservation = new Reservation(name, newPhone, null, null, null, null, null, null, null, null, false);

        // Execute
        personsListManager.updatePersonsListAfterEdit(oldReservation, newReservation);

        // Verify old phone decremented
        Person oldResult = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(oldPhone.value)) {
                oldResult = p;
                break;
            }
        }

        assertNotNull(oldResult);
        assertEquals(1, oldResult.getCounter());

        // Verify new phone added
        Person newResult = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(newPhone.value)) {
                newResult = p;
                break;
            }
        }

        assertNotNull(newResult);
        assertEquals(1, newResult.getCounter());
    }

    @Test
    public void updatePersonsListAfterEdit_phoneChangeOldCounterOne() {
        // Setup
        Name name = new Name("Test Name");
        Phone oldPhone = new Phone("12345678");
        Phone newPhone = new Phone("87654321");

        Person person = new Person(name, oldPhone);
        person.setCounter(1);
        personsList.addPerson(person);

        Reservation oldReservation = new Reservation(name, oldPhone, null, null, null, null, null, null, null, null, false);
        Reservation newReservation = new Reservation(name, newPhone, null, null, null, null, null, null, null, null, false);

        // Execute
        personsListManager.updatePersonsListAfterEdit(oldReservation, newReservation);

        // Verify old phone removed
        Person oldResult = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(oldPhone.value)) {
                oldResult = p;
                break;
            }
        }

        assertNull(oldResult);

        // Verify new phone added
        Person newResult = null;
        for (Person p : personsList.getPersonsList()) {
            if (p.getPhone().value.equals(newPhone.value)) {
                newResult = p;
                break;
            }
        }

        assertNotNull(newResult);
        assertEquals(1, newResult.getCounter());
    }
}