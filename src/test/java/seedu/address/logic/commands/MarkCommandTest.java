package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MarkCommand.MESSAGE_MARK_RESERVATION_SUCCESS;
import static seedu.address.testutil.TypicalReservations.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartTime;
import seedu.address.testutil.ReservationBuilder;

public class MarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //partition: id that exist and id that does not exist

    @Test
    public void execute_validCommandAndId_success() {
        Reservation reservationToMark = model.getFilteredReservationList().get(0);
        Identification id = new Identification(reservationToMark.getDate(), reservationToMark.getPhone(),
                reservationToMark.getTime());

        MarkCommand markCommand = new MarkCommand(id);

        String expectedMessage = String.format(MESSAGE_MARK_RESERVATION_SUCCESS,
                Messages.format(reservationToMark));
        Reservation reservationMarked = new ReservationBuilder(reservationToMark)
                .withPaymentStatus(true).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setReservation(reservationToMark, reservationMarked);
        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCommandAndNotExistId_fail() {
        Reservation reservationToMark = model.getFilteredReservationList().get(0);
        Identification id = new Identification(reservationToMark.getDate(), reservationToMark.getPhone(),
                new StartTime("2359"));

        MarkCommand markCommand = new MarkCommand(id);

        String expectedMessage = "Input reservation id does not exist.";

        assertCommandFailure(markCommand, model, expectedMessage);
    }







}
