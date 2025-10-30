package tutman.tuiniverse.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutman.tuiniverse.logic.commands.ViewCommand.MESSAGE_VIEW_SUCCESS;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tutman.tuiniverse.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ModelManager;
import tutman.tuiniverse.model.UserPrefs;
import tutman.tuiniverse.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_filtersLessonsAndReturnsMessage() throws Exception {
        Student studentToViewLessons = (Student) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_VIEW_SUCCESS, Messages.format(studentToViewLessons));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws CommandException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
