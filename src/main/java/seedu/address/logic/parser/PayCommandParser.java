package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object
 */
public class PayCommandParser extends IndexCommandParser<PayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PayCommand
     * and returns a PayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public PayCommandParser() {
        super(PayCommand.MESSAGE_USAGE);
    }

    @Override
    protected PayCommand createCommand(Index index) {
        return new PayCommand(index);
    }
}
