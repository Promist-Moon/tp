package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;

//Solution below adapted from @@author {Damith C.} - PersonListPanel.java
/**
 * Panel containing the list of lessons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;
    @FXML
    private Label title;

    /**
     * Creates a {@code LessonListPanel} with the given {@code ObservableList}.
     */
    public LessonListPanel(ObservableList<Lesson> lessonList, String titleText) {
        super(FXML);
        title.setText(titleText);
        lessonListView.setItems(lessonList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * update the lesson title to specified text.
     * @param title
     */
    public void updateLessonTitle(String title) {
        this.title.setText(title);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TodayLessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }

}
