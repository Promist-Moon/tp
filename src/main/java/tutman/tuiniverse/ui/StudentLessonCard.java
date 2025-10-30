package tutman.tuiniverse.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tutman.tuiniverse.model.lesson.Lesson;

/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class StudentLessonCard extends UiPart<Region> {

    private static final String FXML = "StudentLessonListCard.fxml";

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
     * Creates a {@code LessonCode} with the given {@code Lesson} and index to display. All lesson card
     * created are for the day that the application is open specifically.
     */
    public StudentLessonCard(Lesson lesson, int index) {
        super(FXML);
        this.lesson = lesson;
        id.setText(index + ". ");
        subject.setText(lesson.getSubject().toString());
        level.setText("Level: Secondary " + lesson.getLevel().toString());
        day.setText("Scheduled every: " + lesson.getDay().toString());
        lessonTime.setText("Time (24H): " + lesson.getLessonTime().toString());
        rate.setText("Rate (per hr): $" + lesson.getRate().toString());

    }
}
