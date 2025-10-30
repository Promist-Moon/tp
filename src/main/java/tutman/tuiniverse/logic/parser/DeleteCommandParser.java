package tutman.tuiniverse.logic.parser;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.commands.DeleteCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser extends IndexCommandParser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommandParser() {
        super(DeleteCommand.MESSAGE_USAGE);
    }

    @Override
    protected DeleteCommand createCommand(Index index) {
        return new DeleteCommand(index);
    }
}
