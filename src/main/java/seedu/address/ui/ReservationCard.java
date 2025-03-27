package seedu.address.ui;

import java.util.Comparator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.PersonsList;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Reservation;


/**
 * A UI component that displays information of a {@code Person}.
 */
public class ReservationCard extends UiPart<Region> {

    private static final String FXML = "ReservationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reservation reservation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label duration;
    @FXML
    private Label pax;
    @FXML
    private Label table;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private Label reservationId;
    @FXML
    private Label isPaid;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ReservationCard(Reservation reservation, int displayedIndex) {
        super(FXML);
        this.reservation = reservation;
        id.setText(displayedIndex + ". ");
        name.setText(reservation.getName().getFullName());
        phone.setText(reservation.getPhone().value);
        date.setText(reservation.getDate().value);
        time.setText(reservation.getTime().value);
        duration.setText(reservation.getDuration().value);
        pax.setText(reservation.getPax().value);
        table.setText(reservation.getTable().value);
        remark.setText(reservation.getRemark().value);
        reservation.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        reservationId.setText(reservation.getId().value);
        isPaid.setText(reservation.getIsPaid() ? "Paid" : "Not Paid");
        updateCardStyle();
    }

    /**
     * Updates the background color of the reservation card based on payment status.
     */
    private void updateCardStyle() {
        Platform.runLater(() -> {
            System.out.println("Updating card style...");
            cardPane.getStyleClass().removeAll("normal", "regular");

            Person p = PersonsList.getPerson(reservation.getPhone());

            if (p == null) {
                System.out.println("❌ Person not found for phone: " + reservation.getPhone());
                return;
            }

            System.out.println("✅ Applying style for: " + p.getName());

            if (p.isRegularAmountHitted()) {
                cardPane.getStyleClass().add("regular");
            } else {
                cardPane.getStyleClass().add("normal");
            }

            // 🔹 Force UI to refresh the node immediately
            cardPane.applyCss();
            cardPane.layout();
            cardPane.setVisible(false);
            cardPane.setVisible(true);
        });
    }
}
