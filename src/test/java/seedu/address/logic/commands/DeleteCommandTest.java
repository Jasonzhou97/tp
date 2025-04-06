package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PersonsList;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model;
    private Model expectedModel;
    private Reservation testReservation;
    private Identification testId;

    @BeforeEach
    public void setUp() {
        // Create model and add a test reservation
        model = new ModelManager();
        expectedModel = new ModelManager();

        // Create a test reservation
        testReservation = new ReservationBuilder().build();
        testId = testReservation.getId();

        // Add the reservation to the model
        model.addReservation(testReservation);
        expectedModel.addReservation(testReservation);
    }

    @Test
    public void execute_validId_success() {
        DeleteCommand deleteCommand = new DeleteCommand(testId);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                Messages.format(testReservation));

        // Delete from expected model
        expectedModel.deleteReservation(testReservation);
        expectedModel.updatePersonsListAfterDelete(testReservation);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Helper method to get a person's booking count from the model's persons list
     */
    private int getBookingCount(Model model, Name name, Phone phone) {
        PersonsList personsList = model.getPersonsList();
        for (Person person : personsList.getRegularCustomers()) {
            if (person.getName().equals(name) && person.getPhone().equals(phone)) {
                return person.getCounter();
            }
        }
        return 0;
    }
}
