package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;

import tutman.tuiniverse.model.AddressBook;
import tutman.tuiniverse.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All contacts and lessons have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
