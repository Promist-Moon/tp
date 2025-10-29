package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.student.PaymentStatus;
import seedu.address.model.student.StudentMatchesPaymentStatusPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListOverdueCommand extends Command {

    public static final String COMMAND_WORD = "list.overdue";

    public static final String MESSAGE_SUCCESS = "Listed all overdue students";

    private StudentMatchesPaymentStatusPredicate predicate;

    public ListOverdueCommand() {
        this.predicate = new StudentMatchesPaymentStatusPredicate(PaymentStatus.OVERDUE);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonListByPaymentStatus(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
