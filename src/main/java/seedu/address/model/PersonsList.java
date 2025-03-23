package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Person;

/**
 * Wraps all data of persons list
 * Duplicates are not allowed
 */
public class PersonsList implements ReadOnlyPersonsList {

    private final ArrayList<Person> personsList;

    /**
     * Creates an empty PersonsList.
     */
    public PersonsList() {
        this.personsList = new ArrayList<>();
    }

    /**
     * Creates a PersonsList using the Persons in the {@code toBeCopied}
     */
    public PersonsList(ReadOnlyPersonsList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code PersonsList} with {@code newData}.
     */
    public void resetData(ReadOnlyPersonsList newData) {
        requireNonNull(newData);
        this.personsList.clear();
        this.personsList.addAll(newData.getPersonsList());
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the persons list.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        for (Person p : personsList) {
            if (p.equals(person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a person to the persons list.
     * The person must not already exist in the persons list.
     */
    public void addPerson(Person p) {
        personsList.add(p);
    }

    /**
     * Removes {@code key} from this {@code PersonsList}.
     * {@code key} must exist in the persons list.
     */
    public void removePerson(Person key) {
        personsList.remove(key);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the persons list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        int index = personsList.indexOf(target);
        if (index == -1) {
            throw new IllegalArgumentException("Person not found");
        }

        personsList.set(index, editedPerson);
    }

    /**
     * Returns the backing list as an ArrayList.
     */
    @Override
    public ArrayList<Person> getPersonsList() {
        return new ArrayList<>(personsList); // Return a defensive copy
    }

    /**
     * Returns true if both persons lists have the same persons.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonsList // instanceof handles nulls
                && personsList.equals(((PersonsList) other).personsList));
    }

    @Override
    public int hashCode() {
        return personsList.hashCode();
    }

    @Override
    public String toString() {
        return personsList.size() + " persons";
    }
}