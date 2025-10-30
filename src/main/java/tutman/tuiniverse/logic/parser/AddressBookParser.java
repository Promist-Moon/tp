package tutman.tuiniverse.logic.parser;

import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutman.tuiniverse.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutman.tuiniverse.commons.core.LogsCenter;
import tutman.tuiniverse.logic.commands.AddCommand;
import tutman.tuiniverse.logic.commands.AddLessonCommand;
import tutman.tuiniverse.logic.commands.ClearCommand;
import tutman.tuiniverse.logic.commands.Command;
import tutman.tuiniverse.logic.commands.DeleteCommand;
import tutman.tuiniverse.logic.commands.DeleteLessonCommand;
import tutman.tuiniverse.logic.commands.EditCommand;
import tutman.tuiniverse.logic.commands.EditLessonCommand;
import tutman.tuiniverse.logic.commands.ExitCommand;
import tutman.tuiniverse.logic.commands.FindCommand;
import tutman.tuiniverse.logic.commands.HelpCommand;
import tutman.tuiniverse.logic.commands.ListCommand;
import tutman.tuiniverse.logic.commands.ListOverdueCommand;
import tutman.tuiniverse.logic.commands.ListPaidCommand;
import tutman.tuiniverse.logic.commands.ListUnpaidCommand;
import tutman.tuiniverse.logic.commands.PayCommand;
import tutman.tuiniverse.logic.commands.ViewCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListUnpaidCommand.COMMAND_WORD:
            return new ListUnpaidCommand();

        case ListPaidCommand.COMMAND_WORD:
            return new ListPaidCommand();

        case ListOverdueCommand.COMMAND_WORD:
            return new ListOverdueCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case PayCommand.COMMAND_WORD:
            return new PayCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case EditLessonCommand.COMMAND_WORD:
            return new EditLessonCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
