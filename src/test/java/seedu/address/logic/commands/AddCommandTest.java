package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReservations.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.ReservationBuilder;
/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_reservationAcceptedByModel_addSuccessful() {
        Reservation validReservation = new ReservationBuilder().build();

        Model expectedModel = new ModelManager();
        expectedModel.addReservation(validReservation);

        assertCommandSuccess(new AddCommand(validReservation), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validReservation)),
                expectedModel);
    }

    @Test
    public void execute_duplicateReservation_throwsCommandException() {
        Reservation validReservation = new ReservationBuilder().build();
        model.addReservation(validReservation);

        assertCommandFailure(new AddCommand(validReservation), model,
                AddCommand.MESSAGE_DUPLICATE_RESERVATION);
    }

    @Test
    public void execute_addingPersonToPersonsList() {
        Reservation validReservation = new ReservationBuilder().build();
        AddCommand addCommand = new AddCommand(validReservation);

        Model expectedModel = new ModelManager();
        expectedModel.addReservation(validReservation);

        // Verify person is added to persons list
        assertTrue(expectedModel.hasPerson(
                new seedu.address.model.reservation.Person(
                        validReservation.getName(),
                        validReservation.getPhone())));
    }

    @Test
    public void equals() {
        Reservation alice = new ReservationBuilder(ALICE).build();
        Reservation bob = new ReservationBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different reservation -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
