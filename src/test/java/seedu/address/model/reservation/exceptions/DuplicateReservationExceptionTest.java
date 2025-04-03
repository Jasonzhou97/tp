package seedu.address.model.reservation.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


public class DuplicateReservationExceptionTest {

    @Test
    public void exceptionMessage_isCorrect() {
        DuplicateReservationException exception = new DuplicateReservationException();
        assertEquals("Operation would result in duplicate reservations", exception.getMessage());
    }

    @Test
    public void exception_canBeThrownAndCaught() {
        Exception thrown = assertThrows(DuplicateReservationException.class, () -> {
            throw new DuplicateReservationException();
        });
        assertEquals("Operation would result in duplicate reservations", thrown.getMessage());
    }
}
