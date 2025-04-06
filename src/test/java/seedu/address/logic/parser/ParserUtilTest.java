package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {

    @Test
    public void parseName_validValue_success() throws Exception {
        String input = "John Doe";
        Name expected = new Name(input);
        assertEquals(expected, ParserUtil.parseName(input));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(""));
    }

    @Test
    public void parsePhone_validValue_success() throws Exception {
        String input = "98765432";
        Phone expected = new Phone(input);
        assertEquals(expected, ParserUtil.parsePhone(input));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone("123"));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("01/05/2000"));
    }

    @Test
    public void parseTime_validValue_success() throws Exception {
        String input = "1400";
        StartTime expected = new StartTime(input);
        assertEquals(expected, ParserUtil.parseTime(input));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime("25:99"));
    }

    @Test
    public void parseDuration_validValue_success() throws Exception {
        String input = "1.5";
        Duration expected = new Duration(input);
        assertEquals(expected, ParserUtil.parseDuration(input));
    }

    @Test
    public void parseDuration_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration("13"));
    }

    @Test
    public void parsePax_validValue_success() throws Exception {
        String input = "5";
        Pax expected = new Pax(input);
        assertEquals(expected, ParserUtil.parsePax(input));
    }

    @Test
    public void parsePax_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePax("10000"));
    }

    @Test
    public void parseTable_validValue_success() throws Exception {
        String input = "A1";
        Table expected = new Table(input);
        assertEquals(expected, ParserUtil.parseTable(input));
    }

    @Test
    public void parseTable_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTable("-1"));
    }

    @Test
    public void parseRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseRemark("123451234512345123451234512345123451234512345123451234512345123451234512345"));
    }

    @Test
    public void parseTag_validValue_success() throws Exception {
        String input = "Vegan";
        Tag expected = new Tag(input);
        assertEquals(expected, ParserUtil.parseTag(input));
    }
}
