package seedu.address.model;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Phone;
public class Person {
    private final Name name;
    private final Phone phone;
    private int bookingCount;
    private boolean isRegularCustomer;

    public Person(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
        this.bookingCount = 0;
        this.isRegularCustomer = false;
    }

    public Person(Name name, Phone phone, int bookingCount, boolean isRegularCustomer) {
        this.name = name;
        this.phone = phone;
        this.bookingCount = bookingCount;
        this.isRegularCustomer = isRegularCustomer;
    }

    // Existing getters for name and phone

    public int getBookingCount() {
        return bookingCount;
    }

    public boolean isRegularCustomer() {
        return isRegularCustomer;
    }

    public Person incrementBookingCount() {
        int newCount = this.bookingCount + 1;
        boolean newRegularStatus = newCount >= 3; // For example, 3+ bookings = regular customer
        return new Person(this.name, this.phone, newCount, newRegularStatus);
    }

    public Name getName() {
        return this.name;
    }

    public Phone getPhone() {
        return this.phone;
    }

    // Update other methods accordingly (equals, hashCode, toString)
}