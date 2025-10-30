package tutman.tuiniverse.logic.commands;

import static tutman.tuiniverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutman.tuiniverse.testutil.TypicalPersons.ALICE;
import static tutman.tuiniverse.testutil.TypicalPersons.BENSON;
import static tutman.tuiniverse.testutil.TypicalPersons.CARL;
import static tutman.tuiniverse.testutil.TypicalPersons.DANIEL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutman.tuiniverse.model.AddressBook;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.ModelManager;
import tutman.tuiniverse.model.UserPrefs;
import tutman.tuiniverse.model.student.PaymentStatus;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.testutil.AddressBookBuilder;
import tutman.tuiniverse.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListUnpaidCommandTest {

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
    public void execute_listIsNotFiltered_showsUnpaidStudentOnly() {
        //check with updateFilteredPersonList func
        //that should work the same way
        expectedModel.updateFilteredPersonList(person ->
                person instanceof Student && ((Student) person).getPaymentStatus() == PaymentStatus.UNPAID);
        assertCommandSuccess(new ListUnpaidCommand(), model, ListUnpaidCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void execute_listIsFiltered_showsUnpaidStudentOnly() {
        expectedModel.updateFilteredPersonList(person ->
                person instanceof Student && ((Student) person).getPaymentStatus() == PaymentStatus.UNPAID);
        assertCommandSuccess(new ListUnpaidCommand(), model, ListUnpaidCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
