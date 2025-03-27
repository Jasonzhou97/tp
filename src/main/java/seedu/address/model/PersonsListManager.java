package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;

/**
 * Manages operations related to PersonsList that involve coordination with Reservations.
 */
public class PersonsListManager {
    private final PersonsList personsList;

    public PersonsListManager(PersonsList personsList) {
        this.personsList = personsList;
    }
    /**
     * Updates the PersonsList when a reservation is deleted.
     * Decrements the counter for the associated phone number.
     * @param deletedReservation the reservation that was deleted
     */
    public void updatePersonsListAfterDelete(Reservation deletedReservation) {
        // Load current list from file
        personsList.loadListFromFile();

        // Get the phone from the deleted reservation
        Phone phoneToUpdate = deletedReservation.getPhone();

        // Get the current in-memory list after reloading
        ArrayList<Person> currentPersons = new ArrayList<>(personsList.getPersonsList());

        // Find the person with the matching phone
        Person personToUpdate = null;
        for (Person person : currentPersons) {
            if (person.getPhone().value.equals(phoneToUpdate.value)) {
                personToUpdate = person;
                break;
            }
        }

        // If found, decrement counter and update
        if (personToUpdate != null) {
            // Remove old entry regardless
            personsList.removePerson(personToUpdate);

            // Calculate new counter value
            int newCounter = personToUpdate.getCounter() - 1;

            // If counter would be 0, remove the person entirely as essentially it's diff person
            if (newCounter <= 0) {
                // Person is already removed, don't add them back
                // for debug
                System.out.println("Removed person with phone " + phoneToUpdate.value + " as counter is now 0");
            } else {
                // Counter > 0, update and keep the person, another regular
                boolean stillRegular = newCounter >= personsList.getRegularCustomerThreshold();

                Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone());
                updatedPerson.setCounter(newCounter);
                updatedPerson.setIsRegular(stillRegular);

                // Add updated entry
                personsList.addPerson(updatedPerson);
            }

            // Save changes to file in both case
            personsList.savePersonsToFile();
        }
    }
    /**
     * Updates person details in PersonsList when a reservation is edited.
     * @param oldReservation the reservation before editing
     * @param newReservation the reservation after editing
     */
    public void updatePersonsListAfterEdit(Reservation oldReservation, Reservation newReservation) {
        // Check if phone or name have been updated
        boolean phoneChanged = !oldReservation.getPhone().equals(newReservation.getPhone());
        boolean nameChanged = !oldReservation.getName().equals(newReservation.getName());

        // Only proceed if something changed
        if (phoneChanged || nameChanged) {
            // Load current list from file
            personsList.loadListFromFile();

            // Get the current in-memory list after reloading
            ArrayList<Person> currentPersons = new ArrayList<>(personsList.getPersonsList());

            if (phoneChanged) {
                // Find the old phone entry
                Phone oldPhone = oldReservation.getPhone();
                Person oldPerson = null;

                for (Person person : currentPersons) {
                    // Match by phone number
                    if (person.getPhone().value.equals(oldPhone.value)) {
                        oldPerson = person;
                        break;
                    }
                }

                if (oldPerson != null) {
                    // Remove old person from list
                    personsList.removePerson(oldPerson);

                    // Decrement the counter for the old phone number
                    int newCounter = Math.max(0, oldPerson.getCounter() - 1);

                    // If counter is not 0, keep the old person with decremented counter
                    if (newCounter > 0) {
                        boolean stillRegular = newCounter >= personsList.getRegularCustomerThreshold();

                        Person updatedOldPerson = new Person(oldPerson.getName(), oldPhone);
                        updatedOldPerson.setCounter(newCounter);
                        updatedOldPerson.setIsRegular(stillRegular);

                        // Add the updated old person back
                        personsList.addPerson(updatedOldPerson);
                    }

                    // Now handle the new phone number
                    // Check if new phone already exists in PersonsList
                    Phone newPhone = newReservation.getPhone();
                    Person existingNewPerson = null;

                    for (Person person : currentPersons) {
                        if (person.getPhone().value.equals(newPhone.value)) {
                            existingNewPerson = person;
                            break;
                        }
                    }

                    if (existingNewPerson != null) {
                        // Remove existing entry
                        personsList.removePerson(existingNewPerson);

                        // Create updated entry with incremented counter
                        int incrementedCounter = existingNewPerson.getCounter() + 1;
                        boolean nowRegular = incrementedCounter >= personsList.getRegularCustomerThreshold();

                        Person updatedNewPerson = new Person(newReservation.getName(), newPhone);
                        updatedNewPerson.setCounter(incrementedCounter);
                        updatedNewPerson.setIsRegular(nowRegular);

                        // Add updated entry
                        personsList.addPerson(updatedNewPerson);
                    } else {
                        // Create new person with new phone and counter of 1
                        Person newPerson = new Person(newReservation.getName(), newPhone);
                        newPerson.setCounter(1);
                        newPerson.setIsRegular(false);

                        // Add new person
                        personsList.addPerson(newPerson);
                    }
                } else {
                    // Old person not found, just add the new person
                    Person newPerson = new Person(newReservation.getName(), newReservation.getPhone());
                    newPerson.setCounter(1);
                    newPerson.setIsRegular(false);
                    personsList.addPerson(newPerson);
                }
            } else if (nameChanged) {
                // Only name changed - update the existing record
                Phone currentPhone = newReservation.getPhone();
                Person existingPerson = null;

                for (Person person : currentPersons) {
                    if (person.getPhone().value.equals(currentPhone.value)) {
                        existingPerson = person;
                        break;
                    }
                }

                if (existingPerson != null) {
                    // Remove old entry
                    personsList.removePerson(existingPerson);

                    // Create updated entry with new name
                    Person updatedPerson = new Person(newReservation.getName(), currentPhone);
                    updatedPerson.setCounter(existingPerson.getCounter());
                    updatedPerson.setIsRegular(existingPerson.isRegular());

                    // Add updated entry
                    personsList.addPerson(updatedPerson);
                } else {
                    // Just add a new record if no existing one found
                    Person newPerson = new Person(newReservation.getName(), currentPhone);
                    newPerson.setCounter(1);
                    newPerson.setIsRegular(false);
                    personsList.addPerson(newPerson);
                }
            }

            // SAVE FILEE
            personsList.savePersonsToFile();
        }
    }
}
