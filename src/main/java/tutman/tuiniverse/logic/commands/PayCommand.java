package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutman.tuiniverse.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.commons.util.ToStringBuilder;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.payment.exceptions.PaymentException;
import tutman.tuiniverse.model.person.Person;
import tutman.tuiniverse.model.person.student.Student;

/**
 * Marks all payments associated with the specified person as paid.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all payments associated with the specified person as paid,"
            + " using the index number shown in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_PAYMENT_SUCCESS = "Paid: %1$s";
    public static final String MESSAGE_NOT_PAID = "Student has paid already: %1$s";
    public static final String MESSAGE_NOT_STUDENT = "Unsupported person: %1$s";

    private final Index targetIndex;

    public PayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToPay = lastShownList.get(targetIndex.getZeroBased());
        try {
            Person paidStudent = makePayment(studentToPay);
            model.setPerson(studentToPay, paidStudent);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS, Messages.format(paidStudent)));
        } catch (PaymentException e) {
            throw new CommandException(String.format(MESSAGE_NOT_PAID, Messages.format(studentToPay)));
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(MESSAGE_NOT_STUDENT, e.getMessage()));
        }
    }

    private static Person makePayment(Person studentToPay) throws PaymentException {
        assert studentToPay != null;

        // edit for future iterations to accept only student objects straightaway
        if (studentToPay instanceof Student s) {
            return makePayment(s);
        }

        // if not Student (or Parent in future additions)
        throw new IllegalArgumentException(studentToPay.getClass().getSimpleName());
    }

    private static Student makePayment(Student studentToPay) throws PaymentException {
        studentToPay.pay();
        return studentToPay;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PayCommand)) {
            return false;
        }

        PayCommand otherPayCommand = (PayCommand) other;
        return targetIndex.equals(otherPayCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
