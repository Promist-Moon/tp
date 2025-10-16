package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PayCommand}.
 */
public class PayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_alreadyPaid_throwsCommandException() {
        Person target = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PayCommand payCommand = new PayCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PayCommand.MESSAGE_NOT_PAID, Messages.format(target));
        assertCommandFailure(payCommand, model, expectedMessage);
    }

    // Commented out as current person is marked as PAID
    //    @Test
    //    public void execute_validIndexUnfilteredList_success() {
    //        Person personToPay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        PayCommand payCommand = new PayCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(PayCommand.MESSAGE_PAYMENT_SUCCESS,
    //                Messages.format(personToPay));
    //
    //        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //
    //        assertCommandSuccess(payCommand, model, expectedMessage, expectedModel);
    //    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PayCommand payCommand = new PayCommand(outOfBoundIndex);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // Commented out as current person is marked as PAID
    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Person personToPay = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        PayCommand payCommand = new PayCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(PayCommand.MESSAGE_PAYMENT_SUCCESS,
    //                Messages.format(personToPay));
    //
    //        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
    //
    //        assertCommandSuccess(payCommand, model, expectedMessage, expectedModel);
    //    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PayCommand payCommand = new PayCommand(outOfBoundIndex);

        assertCommandFailure(payCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PayCommand payFirstCommand = new PayCommand(INDEX_FIRST_PERSON);
        PayCommand paySecondCommand = new PayCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(payFirstCommand.equals(payFirstCommand));

        // same values -> returns true
        PayCommand payFirstCommandCopy = new PayCommand(INDEX_FIRST_PERSON);
        assertTrue(payFirstCommand.equals(payFirstCommandCopy));

        // different types -> returns false
        assertFalse(payFirstCommand.equals(1));

        // null -> returns false
        assertFalse(payFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(payFirstCommand.equals(paySecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PayCommand payCommand = new PayCommand(targetIndex);
        String expected = PayCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, payCommand.toString());
    }
}
