package tutman.tuiniverse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_DAY_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_START_TIME_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_END_TIME_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_LEVEL_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_SUBJECT_LESSON1;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.VALID_RATE_2;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.showLessonAtIndex;
import static tutman.tuiniverse.testutil.Assert.assertThrows;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static tutman.tuiniverse.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.EditLessonCommand.EditLessonDescriptor;
import tutman.tuiniverse.model.AddressBook;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ModelManager;
import tutman.tuiniverse.model.UserPrefs;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;
import tutman.tuiniverse.testutil.EditLessonDescriptorBuilder;
import tutman.tuiniverse.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditLessonCommand.
 */
public class EditLessonCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withSubject(Subject.fromString(VALID_SUBJECT_LESSON1))
                .build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON, descriptor);
        assertThrows(NullPointerException.class, () -> editLessonCommand.execute(null));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = model.getFilteredLessonList().get(0);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.formatLesson(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(editedLesson.getStudent(), editedLesson, editedLesson);

        assertCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());
        Lesson lastLesson = model.getFilteredLessonList().get(indexLastLesson.getZeroBased());

        LessonBuilder lessonInList = new LessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withSubject(VALID_SUBJECT_LESSON1)
                .withRate(VALID_RATE_2).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withSubject(Subject.fromString(VALID_SUBJECT_LESSON1))
                .withRate(new Rate(VALID_RATE_2))
                .build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(indexLastLesson, INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.formatLesson(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lastLesson.getStudent(), lastLesson, editedLesson);

        assertCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON,
                new EditLessonDescriptor());
        Lesson editedLesson = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.formatLesson(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLessonAtIndex(model, INDEX_FIRST_LESSON);

        Lesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        Lesson editedLesson = new LessonBuilder(lessonInFilteredList).withSubject(VALID_SUBJECT_LESSON1).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder()
                        .withSubject(Subject.fromString(VALID_SUBJECT_LESSON1))
                        .build());

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.formatLesson(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lessonInFilteredList.getStudent(), lessonInFilteredList, editedLesson);

        assertCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLessonUnfilteredList_failure() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(firstLesson).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(INDEX_SECOND_LESSON,
                INDEX_FIRST_LESSON, descriptor);

        assertCommandFailure(editLessonCommand, model, EditLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidLessonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withSubject(Subject.fromString(VALID_SUBJECT_LESSON1))
                .build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(outOfBoundIndex, INDEX_FIRST_LESSON, descriptor);

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
    }

    @Test
    public void execute_invalidLessonIndexFilteredList_failure() {
        showLessonAtIndex(model, INDEX_FIRST_LESSON);
        Index outOfBoundIndex = INDEX_SECOND_LESSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        EditLessonCommand editLessonCommand = new EditLessonCommand(outOfBoundIndex, INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder()
                        .withSubject(Subject.fromString(VALID_SUBJECT_LESSON1))
                        .build());

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
    }

    @Test
    public void equals() {
        final EditLessonCommand standardCommand = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON,
                new EditLessonDescriptor(VALID_DAY_LESSON1, VALID_START_TIME_LESSON1, VALID_END_TIME_LESSON1,
                        VALID_LEVEL_LESSON1, VALID_RATE_2, VALID_SUBJECT_LESSON1));

        // same values -> returns true
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptor(VALID_DAY_LESSON1, VALID_START_TIME_LESSON1,
                VALID_END_TIME_LESSON1, VALID_LEVEL_LESSON1, VALID_RATE_2, VALID_SUBJECT_LESSON1);
        EditLessonCommand commandWithSameValues = new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different lesson index -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(INDEX_SECOND_LESSON, INDEX_FIRST_LESSON, copyDescriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(INDEX_FIRST_LESSON, INDEX_FIRST_LESSON,
                new EditLessonDescriptor(VALID_DAY_LESSON1, VALID_START_TIME_LESSON1, VALID_END_TIME_LESSON1,
                        VALID_LEVEL_LESSON1, VALID_RATE_2, VALID_SUBJECT_LESSON1))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        EditLessonCommand editLessonCommand = new EditLessonCommand(index, index, editLessonDescriptor);
        String expected = EditLessonCommand.class.getCanonicalName() + "{studentIndex=" + index + ", lessonIndex="
                + index + ", editLessonDescriptor=" + editLessonDescriptor + "}";
        assertEquals(expected, editLessonCommand.toString());
    }

}
