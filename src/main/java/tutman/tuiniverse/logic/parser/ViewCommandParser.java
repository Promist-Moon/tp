package tutman.tuiniverse.logic.parser;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.commands.ViewCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser extends IndexCommandParser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ViewCommandParser() {
        super(ViewCommand.MESSAGE_USAGE);
    }

    @Override
    protected ViewCommand createCommand(Index index) {
        return new ViewCommand(index);
    }
}
