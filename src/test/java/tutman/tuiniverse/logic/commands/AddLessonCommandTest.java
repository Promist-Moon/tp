package tutman.tuiniverse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static tutman.tuiniverse.logic.commands.AddLessonCommand.MESSAGE_SUCCESS;
import static tutman.tuiniverse.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.testutil.LessonBuilder;
import tutman.tuiniverse.testutil.StudentBuilder;
import tutman.tuiniverse.testutil.stubs.ModelStub;

public class AddLessonCommandTest {

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(Index.fromZeroBased(0), null));
    }

    @Test
    public void execute_validLessonAndStudentIndex_addSuccessful() throws Exception {
        Lesson validLesson = new LessonBuilder().build();
        Student targetStudent = new StudentBuilder().build();

        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded(targetStudent);

        AddLessonCommand command = new AddLessonCommand(Index.fromZeroBased(0), validLesson);
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_SUCCESS, Messages.formatLesson(validLesson)), result.getFeedbackToUser());
        assertEquals(1, modelStub.lessonsAdded.size());
        assertEquals(validLesson, modelStub.lessonsAdded.get(0));
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Lesson validLesson = new LessonBuilder().build();

        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();

        AddLessonCommand command = new AddLessonCommand(Index.fromZeroBased(5), validLesson);

        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Lesson lessonA = new LessonBuilder().withStudent(alice).withLessonTime("10:00", "12:00").build();
        Lesson lessonB = new LessonBuilder().withStudent(alice).withLessonTime("12:00", "14:00").build();

        AddLessonCommand addLessonACommand = new AddLessonCommand(Index.fromZeroBased(0), lessonA);
        AddLessonCommand addLessonBCommand = new AddLessonCommand(Index.fromZeroBased(1), lessonB);

        // same object -> returns true
        assertTrue(addLessonACommand.equals(addLessonACommand));

        // same values -> returns true
        AddLessonCommand addLessonACommandCopy = new AddLessonCommand(Index.fromZeroBased(0), lessonA);
        assertTrue(addLessonACommand.equals(addLessonACommandCopy));

        // different types -> returns false
        assertFalse(addLessonACommand.equals(1));

        // null -> returns false
        assertFalse(addLessonACommand.equals(null));

        // different lesson -> returns false
        assertFalse(addLessonACommand.equals(addLessonBCommand));

    }

    /**
     * A Model stub that always accepts the added lesson.
     */
    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final List<Lesson> lessonsAdded = new ArrayList<>();
        final List<Student> students;

        ModelStubAcceptingLessonAdded(Student... students) {
            this.students = List.of(students);
        }

        ModelStubAcceptingLessonAdded() {
            this.students = List.of();
        }

        @Override
        public ObservableList<Student> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(students);
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            return lessonsAdded.stream().anyMatch(lesson::equals);
        }

        @Override
        public void addLesson(Student target, Lesson lesson) {
            lessonsAdded.add(lesson);
        }
    }
}
