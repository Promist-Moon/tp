package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;

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

    public final Person person;

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
    private VBox lessonList;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        // Support Student subclass only (Parent not implemented yet)
        if (person instanceof Student student) {
            address.setText(student.getAddress().value);
            address.setVisible(true);
            address.setManaged(true);
            tags.getChildren().clear();
            tags.setVisible(true);
            tags.setManaged(true);
            student.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            paymentStatus.setText(((Student) person).getPaymentStatus().toString());
            switch (student.getPaymentStatus()) {
            case PAID:
                paymentStatus.getStyleClass().add("payment-status-paid");
                break;
            case UNPAID:
                paymentStatus.getStyleClass().add("payment-status-unpaid");
                break;
            case OVERDUE:
                paymentStatus.getStyleClass().add("payment-status-overdue");
                break;
            default:
                break;
            }
            // lessonList.getChildren().add(new LessonListCard(student.getLessons()).getRoot());
        } else {
            // Fallback: hide address and tags if not a Student
            address.setVisible(false);
            address.setManaged(false);
            tags.getChildren().clear();
            tags.setVisible(false);
            tags.setManaged(false);
        }
    }
}
