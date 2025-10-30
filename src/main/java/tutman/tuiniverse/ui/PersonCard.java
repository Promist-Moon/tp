package tutman.tuiniverse.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tutman.tuiniverse.model.student.Student;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label paymentStatus;
    @FXML
    private Label unpaidAmount;
    @FXML
    private VBox lessonList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Student student, int displayedIndex) {
        super(FXML);
        this.person = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        email.setText(student.getEmail().value);
        address.setText(student.getAddress().value);
        address.setVisible(true);
        address.setManaged(true);
        tags.getChildren().clear();
        tags.setVisible(true);
        tags.setManaged(true);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        paymentStatus.setText(((Student) student).getPaymentStatus().toString());
        unpaidAmount.setText(String.format("$" + student.getAmountDue()));
        unpaidAmount.setVisible(true);
        unpaidAmount.setManaged(true);
        switch (student.getPaymentStatus()) {
        case PAID:
            paymentStatus.getStyleClass().add("payment-status-paid");
            unpaidAmount.getStyleClass().add("cell_small_label_paid");
            break;
        case UNPAID:
            paymentStatus.getStyleClass().add("payment-status-unpaid");
            unpaidAmount.getStyleClass().add("cell_small_label_unpaid");
            break;
        case OVERDUE:
            paymentStatus.getStyleClass().add("payment-status-overdue");
            unpaidAmount.getStyleClass().add("cell_small_label_overdue");
            break;
        default:
            break;
        }
    }
}
