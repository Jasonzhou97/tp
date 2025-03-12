package seedu.address.model.reservation;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartDateTest {
    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Invalid date formats
        String[] invalidDates = {
            "32/01/2024", // Invalid day
            "31/13/2024", // Invalid month
            "1/1/2024", // Missing leading zeros
            "29/02/2023", // Invalid leap year date
            "31/04/2024", // April only has 30 days
            "00/12/2024", // 00 is not a valid day
            "12/00/2024", // 00 is not a valid month
            "" // Empty string
        };

        for (String date : invalidDates) {
            assertThrows(IllegalArgumentException.class, () -> new StartDate(date));
        }
    }
}
