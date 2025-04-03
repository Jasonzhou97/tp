package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.GastroBook;
import seedu.address.model.ReadOnlyGastroBook;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Reservation[] getSampleReservations() {
        return new Reservation[] {
        };
    }

    public static ReadOnlyGastroBook getSampleAddressBook() {
        GastroBook sampleAb = new GastroBook();
        for (Reservation sampleReservation : getSampleReservations()) {
            sampleAb.addReservation(sampleReservation);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
