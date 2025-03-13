package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;

public class EditReservationDescriptorTest {

    @Test
    public void equals() {
        /*
        // same values -> returns true
        EditCommand.EditReservationDescriptor descriptorWithSameValues = new EditReservationDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditReservationDescriptor editedAmy = new EditReservationDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditReservationDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditReservationDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

         */
    }

    @Test
    public void toStringMethod() {
        /*
        EditCommand.EditReservationDescriptor editReservationDescriptor = new EditReservationDescriptor();
        String expected = EditCommand.EditReservationDescriptor.class.getCanonicalName() + "{name="
                + editReservationDescriptor.getName().orElse(null) + ", phone="
                + editReservationDescriptor.getPhone().orElse(null) + ", email="
                + editReservationDescriptor.getDate().orElse(null) + ", date="
                + editReservationDescriptor.getTime().orElse(null) + ", time="
                + editReservationDescriptor.getDuration().orElse(null) + ", duration="
                + editReservationDescriptor.getPax().orElse(null) + ", pax="
                + editReservationDescriptor.getTable().orElse(null) + ", table="
                + editReservationDescriptor.getTags().orElse(null) + ", id="
                + editReservationDescriptor.getId().orElse(null) + "}";
        assertEquals(expected, editReservationDescriptor.toString());
        */
    }
}
