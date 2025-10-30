package tutman.tuiniverse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutman.tuiniverse.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ModelManager;
import tutman.tuiniverse.model.UserPrefs;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.testutil.LessonBuilder;
import tutman.tuiniverse.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // Add a student with lessons for testing
        Student student = new StudentBuilder().build();
        Lesson lesson1 = new LessonBuilder().withSubject("Mother Tongue")
                .withLessonTime("19:00", "21:00").withDay("4").build();
        Lesson lesson2 = new LessonBuilder().withSubject("Literature")
                .withLessonTime("15:00", "17:00").withDay("6").build();
    }

    @Test
    public void execute_validIndexes_success() {
        int lastIndex = model.getFilteredPersonList().size();
        Index studentIndex = Index.fromOneBased(lastIndex);
        Student student = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());

        Index lessonIndex = Index.fromOneBased(1);
        Lesson lessonToDelete = student.getLessonList().getLessons().get(0);

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(studentIndex, lessonIndex);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.formatLesson(lessonToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index lessonIndex = Index.fromOneBased(1);

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, lessonIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Student student = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());

        Index outOfBoundLessonIndex = Index.fromOneBased(student.getLessonList().getSize() + 1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(studentIndex, outOfBoundLessonIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
    }

    @Test
    public void equals() {
        Index studentIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(1);
        DeleteLessonCommand deleteFirstLesson = new DeleteLessonCommand(studentIndex, lessonIndex);
        DeleteLessonCommand deleteSecondLesson = new DeleteLessonCommand(studentIndex,
                Index.fromOneBased(2));
        DeleteLessonCommand deleteThirdLesson = new DeleteLessonCommand(Index.fromOneBased(2),
                lessonIndex);

        // same object -> returns true
        assertEquals(deleteFirstLesson, deleteFirstLesson);

        // same values -> returns true
        DeleteLessonCommand deleteLessonFirstCommandCopy = new DeleteLessonCommand(studentIndex, lessonIndex);
        assertEquals(deleteFirstLesson, deleteLessonFirstCommandCopy);

        // different lesson index -> returns false
        assert(!deleteFirstLesson.equals(deleteSecondLesson));

        // different student index -> returns false
        assert(!deleteFirstLesson.equals(deleteThirdLesson));

        // null -> returns false
        assert(deleteFirstLesson != null);

        // different object type -> returns false
        assert(!deleteFirstLesson.equals("some string"));
    }


}
