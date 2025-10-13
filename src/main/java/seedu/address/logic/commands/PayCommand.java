package seedu.address.logic.commands;

//import static java.util.Objects.requireNonNull;
//
//import java.util.List;

import seedu.address.commons.core.index.Index;
//import seedu.address.commons.util.ToStringBuilder;
//import seedu.address.logic.Messages;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.person.Person;


/**
 * Marks all payments associated with the specified person as paid.
 */
public class PayCommand {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all payments associated with the specified person as paid,"
            + " using the index number shown in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_PAYMENT_SUCCESS = "Paid: %1$s";
    private final Index targetIndex;

    public PayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        requireNonNull(model);
//        List<Person> lastShownList = model.getFilteredPersonList();
//
//        if (targetIndex.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        Person personToMarkPaid = lastShownList.get(targetIndex.getZeroBased());
//        model.markPersonPaid(personToMark);
//        return
//    }

}
