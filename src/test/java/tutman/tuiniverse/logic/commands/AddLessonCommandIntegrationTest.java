package tutman.tuiniverse.logic.commands;

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
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddLessonCommandIntegrationTest {
    private Model model;
    private Lesson newLesson;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        newLesson = new LessonBuilder()
                .withSubject("Math")
                .withLevel("4")
                .withDay("6")
                .withLessonTime("12:00", "14:00")
                .build();
    }

    @Test
    public void execute_newLesson_success() {
        // Assume the first person in list is a Student
        Student student = (Student) model.getFilteredPersonList().get(0);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student expectedStudent = new StudentBuilder(student).build();
        expectedModel.setPerson(student, expectedStudent);

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0), newLesson);
        newLesson.addStudent(expectedStudent);
        String expectedMessage = String.format(AddLessonCommand.MESSAGE_SUCCESS, Messages.formatLesson(newLesson));
        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Student student = model.getFilteredPersonList().get(0);
        Lesson lesson = student.getLessonList().getLessons().get(0);
        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromZeroBased(0), lesson);
        assertCommandFailure(addLessonCommand, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        // Index greater than the typicalPersons list size
        Index invalidIndex = Index.fromZeroBased(7);

        AddLessonCommand addLessonCommand = new AddLessonCommand(invalidIndex, newLesson);

        assertCommandFailure(addLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
