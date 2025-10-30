package tutman.tuiniverse.logic.parser;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.commands.PayCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;

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
