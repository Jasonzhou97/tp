package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class StartDateTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    @Test
    public void isValidDateRange_validDates_returnTrue() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // Ensure today's and tomorrow's date are considered valid
        assertTrue(StartDate.isValidDate(today.format(FORMATTER)), "Today's date should be valid");
        assertTrue(StartDate.isValidDate(tomorrow.format(FORMATTER)), "Tomorrow's date should be valid");
    }

    @Test
    public void isValidDateRange_invalidDates_returnFalse() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate nextWeek = today.plusDays(7);

        // Past date should be invalid
        assertFalse(StartDate.isValidDate(yesterday.format(FORMATTER)), "Yesterday's date should be invalid");

        // Future dates beyond tomorrow should be invalid
        assertFalse(StartDate.isValidDate(nextWeek.format(FORMATTER)), "A date after tomorrow should be invalid");

        // Invalid date formats
        assertFalse(StartDate.isValidDate("32/01/2024"), "Invalid day should be rejected");
        assertFalse(StartDate.isValidDate("2024-03-10"), "Incorrect format should be rejected");
        assertFalse(StartDate.isValidDate("10-03-2024"), "Dash format should be rejected");

    }

}
