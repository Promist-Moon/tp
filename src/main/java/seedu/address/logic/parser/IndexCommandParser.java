package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Generic parser for commands that take a single Index argument.
 *
 * @param <T> the command type
 */
public abstract class IndexCommandParser<T> implements Parser<T> {

    private final String messageUsage;

    protected IndexCommandParser(String messageUsage) {
        this.messageUsage = messageUsage;
    }

    /**
     * Constructs the concrete command instance.
     *
     * @param index an Index object.
     */
    protected abstract T createCommand(Index index);

    @Override
    public T parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return createCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage), pe);
        }
    }
}
