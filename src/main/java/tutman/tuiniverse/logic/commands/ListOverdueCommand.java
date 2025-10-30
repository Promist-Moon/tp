package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;

import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.student.PaymentStatus;
import tutman.tuiniverse.model.student.StudentMatchesPaymentStatusPredicate;

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
