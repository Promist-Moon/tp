package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;

public class LessonListCard extends UiPart<VBox> {
    private static final String FXML = "LessonListPanel.fxml";

    @FXML
    private VBox lessonList;

    public LessonListCard(List<Lesson> lessons) {
        super(FXML);
        for (Lesson lesson : lessons) {
            lessonList.getChildren().add(new LessonCard(lesson).getRoot());
        }
    }
}