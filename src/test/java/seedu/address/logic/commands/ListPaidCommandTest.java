package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.PaymentStatus;
import seedu.address.model.student.Student;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.StudentBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListPaidCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        Student alicePaid = new StudentBuilder(ALICE).build();
        alicePaid.setPaymentStatus(PaymentStatus.PAID);
        Student bensonPaid = new StudentBuilder(BENSON).build();
        bensonPaid.setPaymentStatus(PaymentStatus.PAID);

        Student carlOverdue = new StudentBuilder(CARL).build();
        carlOverdue.setPaymentStatus(PaymentStatus.OVERDUE);
        Student danielUnpaid = new StudentBuilder(DANIEL).build();
        danielUnpaid.setPaymentStatus(PaymentStatus.UNPAID);

        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(alicePaid)
                .withPerson(bensonPaid)
                .withPerson(carlOverdue)
                .withPerson(danielUnpaid).build();

        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    }

    @Test
    public void execute_listIsNotFiltered_showsPaidStudentOnly() {
        //check with updateFilteredPersonList func
        //that should work the same way
        expectedModel.updateFilteredPersonList(person ->
                person instanceof Student && ((Student) person).getPaymentStatus() == PaymentStatus.PAID);


        assertCommandSuccess(new ListPaidCommand(), model, ListPaidCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsPaidStudentOnly() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        expectedModel.updateFilteredPersonList(person ->
                person instanceof Student && ((Student) person).getPaymentStatus() == PaymentStatus.PAID);
        assertCommandSuccess(new ListPaidCommand(), model, ListPaidCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
