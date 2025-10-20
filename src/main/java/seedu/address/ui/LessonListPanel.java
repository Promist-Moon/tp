package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

//Solution below adapted from @@author {Damith C.} - PersonListPanel.java
/**
 * Panel containing the list of persons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public LessonListPanel(ObservableList<Lesson> LessonList) {
        super(FXML);
        lessonListView.setItems(LessonList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }

}
