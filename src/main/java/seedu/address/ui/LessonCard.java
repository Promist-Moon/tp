package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label subject;
    @FXML
    private Label level;
    @FXML
    private Label day;
    @FXML
    private Label lessonTime;
    @FXML
    private Label rate;


    /**
     * Creates a {@code LessonCode} with the given {@code Lesson} and index to display.
     */
    public LessonCard(Lesson lesson) {
        super(FXML);
        this.lesson = lesson;
//        id.setText(lesson.getLessonIndex());
        subject.setText(lesson.getSubject().toString());
        level.setText(lesson.getLevel().toString());
        day.setText(lesson.getDay().toString());
        lessonTime.setText(lesson.getLessonTime().toString());
        rate.setText(lesson.getRate().toString());

    }
}
