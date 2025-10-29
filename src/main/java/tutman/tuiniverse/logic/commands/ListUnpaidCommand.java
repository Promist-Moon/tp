package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;

import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.person.student.PaymentStatus;
import tutman.tuiniverse.model.person.student.StudentMatchesPaymentStatusPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListUnpaidCommand extends Command {

    public static final String COMMAND_WORD = "list.unpaid";

    public static final String MESSAGE_SUCCESS = "Listed all unpaid persons";

    private StudentMatchesPaymentStatusPredicate predicate;

    public ListUnpaidCommand() {
        this.predicate = new StudentMatchesPaymentStatusPredicate(PaymentStatus.UNPAID);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonListByPaymentStatus(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
