package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reservation.Name;
import seedu.address.model.Person;
import seedu.address.model.reservation.Phone;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

        private final String name;
        private final String phone;
        private final int bookingCount;
        private final boolean isRegularCustomer;

        @JsonCreator
        public JsonAdaptedPerson(@JsonProperty("name") String name,
                                 @JsonProperty("phone") String phone,
                                 @JsonProperty("bookingCount") int bookingCount,
                                 @JsonProperty("isRegularCustomer") boolean isRegularCustomer) {
            this.name = name;
            this.phone = phone;
            this.bookingCount = bookingCount;
            this.isRegularCustomer = isRegularCustomer;
        }

        public JsonAdaptedPerson(Person source) {
            name = source.getName().fullName;
            phone = source.getPhone().value;
            bookingCount = source.getBookingCount();
            isRegularCustomer = source.isRegularCustomer();
        }

        public Person toModelType() throws IllegalValueException {
            // Validation code for name and phone as before
            final Name modelName = new Name(name);
            final Phone modelPhone = new Phone(phone);

            return new Person(modelName, modelPhone, bookingCount, isRegularCustomer);
        }
    }