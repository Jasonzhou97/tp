package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReservationBuilder;

public class TimeMatchesPredicateTest {

    @Test
    public void test_timeWithinReservation_returnsTrue() {
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(LocalTime.of(13, 30)); // 1:30 PM

        Reservation reservation = new ReservationBuilder()
                .withTime("1300")
                .withDuration("2")
                .build();

        assertTrue(predicate.test(reservation));
    }

    @Test
    public void test_timeBeforeStart_returnsFalse() {
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(LocalTime.of(11, 30)); // 11:30 AM

        Reservation reservation = new ReservationBuilder()
                .withTime("1200")
                .withDuration("1")
                .build();

        assertFalse(predicate.test(reservation));
    }

    @Test
    public void test_timeAfterEnd_returnsFalse() {
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(LocalTime.of(15, 0)); // 3:00 PM

        Reservation reservation = new ReservationBuilder()
                .withTime("1300")
                .withDuration("1")
                .build();

        assertFalse(predicate.test(reservation));
    }

    @Test
    public void test_timeExactlyAtStart_returnsTrue() {
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(LocalTime.of(14, 0)); // 2:00 PM

        Reservation reservation = new ReservationBuilder()
                .withTime("1400")
                .withDuration("2")
                .build();

        assertTrue(predicate.test(reservation));
    }

    @Test
    public void test_timeExactlyAtEnd_returnsFalse() {
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(LocalTime.of(15, 0)); // 3:00 PM

        Reservation reservation = new ReservationBuilder()
                .withTime("1400")
                .withDuration("1")
                .build();

        assertFalse(predicate.test(reservation)); // end is exclusive
    }

    @Test
    public void equals() {
        TimeMatchesPredicate predicate1 = new TimeMatchesPredicate(LocalTime.of(12, 0));
        TimeMatchesPredicate predicate2 = new TimeMatchesPredicate(LocalTime.of(12, 0));
        TimeMatchesPredicate predicate3 = new TimeMatchesPredicate(LocalTime.of(13, 0));

        assertTrue(predicate1.equals(predicate1)); // same object
        assertTrue(predicate1.equals(predicate2)); // same value
        assertFalse(predicate1.equals(predicate3)); // different value
        assertFalse(predicate1.equals(null)); // null
        assertFalse(predicate1.equals("not a predicate")); // different type
    }
}
