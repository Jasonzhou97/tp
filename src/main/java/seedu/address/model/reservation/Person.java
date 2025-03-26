package seedu.address.model.reservation;

import static seedu.address.model.PersonsList.REGULAR_CUSTOMER_THRESHOLD;

/**
 * Represents a person who has made a reservation.
 * Stores information about the person including their name, phone number,
 * and their status as a regular customer.
 */
public class Person {
    private Phone phone;
    private Name name;
    private boolean isRegular = false;
    private int counter = 0;
    private int regularNum = 3;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public Person() {
        // Required for Jackson deserialization
    }

    /**
     * Constructs a {@code Person} with the specified {@code Name} and {@code Phone}.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     */
    public Person(Name name, Phone phone) {
        this.phone = phone;
        this.name = name;
    }

    /**
     * Returns the phone object associated with this person.
     *
     * @return The phone object.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the phone number as a string.
     *
     * @return The phone number string.
     */
    public String getPhoneNumber() {
        return phone.value;
    }

    /**
     * Returns the name of the person.
     *
     * @return The name object.
     */
    public Name getName() {
        return name;
    }

    /**
     * Checks if the person is marked as a regular customer.
     *
     * @return {@code true} if the person is a regular customer, {@code false} otherwise.
     */
    public boolean isRegular() {
        return isRegular;
    }

    /**
     * Checks if the person has met the threshold to be considered a regular customer.
     *
     * @return {@code true} if the number of visits reaches or exceeds the threshold, {@code false} otherwise.
     */
    public boolean isRegularAmountHitted() {
        return this.counter >= REGULAR_CUSTOMER_THRESHOLD;
    }

    /**
     * Sets the regular customer status of the person.
     *
     * @param isRegular {@code true} to mark the person as a regular customer, {@code false} otherwise.
     */
    public void setIsRegular(boolean isRegular) {
        this.isRegular = isRegular;
    }

    /**
     * Returns the count of reservations made by this person.
     *
     * @return The count of reservations.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Sets the count of reservations made by this person.
     *
     * @param counter The number of reservations.
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
}
