package seedu.address.testutil;

import seedu.address.model.GastroBook;
import seedu.address.model.reservation.Reservation;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class GastroBookBuilder {

    private GastroBook gastroBook;

    public GastroBookBuilder() {
        gastroBook = new GastroBook();
    }

    public GastroBookBuilder(GastroBook gastroBook) {
        this.gastroBook = gastroBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public GastroBookBuilder withPerson(Reservation reservation) {
        gastroBook.addReservation(reservation);
        return this;
    }

    public GastroBook build() {
        return gastroBook;
    }
}
