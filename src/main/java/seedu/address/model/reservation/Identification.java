package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Date;

/**
 * Represents an identification number in the reservation system.
 * Ensures that the ID consists only of integers.
 */
public class Identification {
    public static final String MESSAGE_CONSTRAINTS =
            "Identification should only contain integers";

    public static final String VALIDATION_REGEX = "^-?\\d+$";

    public final String value;

    /**
     * Constructs an {@code Identification} using a start date and phone number.
     *
     * @param date  A valid start date.
     * @param phone A valid phone number.
     */
    public Identification(StartDate date, Phone phone) {
        requireNonNull(date);
        requireNonNull(phone);
        // Date and phone format have already been validated
        value = date.toWithoutSlashString()
                + phone.getLastFourDigitsString();
    }


    /**
     * Returns true if a given string is a valid ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Identification)) {
            return false;
        }

        Identification otherId = (Identification) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
