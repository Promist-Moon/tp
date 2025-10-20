package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class TodayLessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

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
    private Label lessonTime;
    @FXML
    private Label rate;


    /**
     * Creates a {@code LessonCode} with the given {@code Lesson} and index to display. All lesson card
     * created are for the day that the application is open specifically.
     */
    public TodayLessonCard(Lesson lesson, int index) {
        super(FXML);
        this.lesson = lesson;
        id.setText(index + ". ");
        subject.setText(lesson.getSubject().toString());
        level.setText("Level: Secondary" + lesson.getLevel().toString());
        lessonTime.setText("Time (24H): " + lesson.getLessonTime().toString());
        rate.setText("Rate (per hr): $" + lesson.getRate().toString());

    }
}
