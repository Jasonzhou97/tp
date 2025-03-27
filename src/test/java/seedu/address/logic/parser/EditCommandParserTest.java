package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PAX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PAX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PAX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TABLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TABLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TABLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TABLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditReservationDescriptor;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditReservationDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "2603202523451200", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "2603202523451200" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "2603202523451200" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "2603202523451200" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag


        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "2603202523451200" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "2603202523451200" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "2603202523451200" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "2603202523451200" + INVALID_NAME_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_ID_BOB + NAME_DESC_AMY + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + DATE_DESC_BOB + PAX_DESC_BOB + TAG_DESC_FRIEND + DURATION_DESC_BOB
                + TABLE_DESC_BOB + TIME_DESC_AMY;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withDuration(VALID_DURATION_BOB).withPax(VALID_PAX_BOB)
                .withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withTable(VALID_TABLE_BOB).withTime(VALID_TIME_AMY).build();

        Identification id = new Identification(VALID_ID_BOB);

        EditCommand expectedCommand = new EditCommand(id, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {

        String userInput = VALID_ID_BOB + PHONE_DESC_BOB + TIME_DESC_AMY;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withTime(VALID_TIME_AMY).build();

        Identification id = new Identification(VALID_ID_BOB);
        EditCommand expectedCommand = new EditCommand(id, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Identification id = new Identification(VALID_ID_AMY);
        String strID = VALID_ID_AMY;
        String userInput = strID + NAME_DESC_AMY;
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = strID + PHONE_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = strID + DATE_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = strID + TIME_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withTime(VALID_TIME_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // pax
        userInput = strID + PAX_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withPax(VALID_PAX_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = strID + DURATION_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withDuration(VALID_DURATION_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // table
        userInput = strID + TABLE_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withTable(VALID_TABLE_AMY).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = strID + TAG_DESC_FRIEND;
        descriptor = new EditReservationDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(id, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        String strID = VALID_ID_BOB;
        String userInput = strID + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = strID + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = strID + PHONE_DESC_AMY + DATE_DESC_AMY + TIME_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + DATE_DESC_AMY + TIME_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + DATE_DESC_BOB + TIME_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_DATE, PREFIX_TIME));

        // multiple invalid values
        userInput = strID + INVALID_PHONE_DESC + INVALID_TIME_DESC + INVALID_PAX_DESC
                + INVALID_PHONE_DESC + INVALID_TIME_DESC + INVALID_PAX_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_TIME, PREFIX_PAX));
    }

    @Test
    public void parse_resetTags_success() {
        Identification id = new Identification(VALID_ID_BOB);
        String userInput = VALID_ID_BOB + TAG_EMPTY;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(id, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
