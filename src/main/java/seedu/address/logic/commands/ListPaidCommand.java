package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.student.PaymentStatus;
import seedu.address.model.person.student.StudentMatchesPaymentStatusPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPaidCommand extends Command {

    public static final String COMMAND_WORD = "list.paid";

    public static final String MESSAGE_SUCCESS = "Listed all paid students";

    private StudentMatchesPaymentStatusPredicate predicate;

    public ListPaidCommand() {
        this.predicate = new StudentMatchesPaymentStatusPredicate(PaymentStatus.PAID);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonListByPaymentStatus(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
