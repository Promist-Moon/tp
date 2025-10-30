package tutman.tuiniverse.logic.commands;

import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutman.tuiniverse.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tutman.tuiniverse.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ModelManager;
import tutman.tuiniverse.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
