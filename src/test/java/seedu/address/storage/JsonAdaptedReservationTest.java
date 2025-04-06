package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.TypicalReservations;

public class JsonAdaptedReservationTest {

    private static final Reservation BENSON = TypicalReservations.BENSON;

    private static final String INVALID_NAME = "R@chelR@chelR@chelR@chelR@chelR@chelR@chelR@chelR@chelR@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_DATE = "32-12-2024";
    private static final String INVALID_TIME = "25:61";
    private static final String INVALID_DURATION = "5h";
    private static final String INVALID_PAX = "-2";
    private static final String INVALID_TABLE = "T@ble9";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ID = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_TIME = BENSON.getTime().toString();
    private static final String VALID_DURATION = BENSON.getDuration().toString();
    private static final String VALID_PAX = BENSON.getPax().toString();
    private static final String VALID_TABLE = BENSON.getTable().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_ID = BENSON.getId().toString();
    private static final Boolean VALID_IS_PAID = BENSON.getIsPaid();

    @Test
    public void toModelType_validReservationDetails_returnsReservation() throws Exception {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(BENSON);
        assertEquals(BENSON, reservation.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                INVALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                null, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, INVALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, null, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, INVALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, null, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, INVALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, null, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, INVALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, null,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidPax_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                INVALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullPax_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                null, VALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTable_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, INVALID_TABLE, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullTable_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, null, VALID_REMARK, VALID_TAGS, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = List.of(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, invalidTags, VALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, INVALID_ID, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(
                VALID_NAME, VALID_PHONE, VALID_DATE, VALID_TIME, VALID_DURATION,
                VALID_PAX, VALID_TABLE, VALID_REMARK, VALID_TAGS, null, VALID_IS_PAID);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }
}
