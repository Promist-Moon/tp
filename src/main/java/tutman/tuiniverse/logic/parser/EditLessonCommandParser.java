package tutman.tuiniverse.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_DAY;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_END_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LEVEL;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_RATE;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_START_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.NoSuchElementException;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.commands.EditLessonCommand;
import tutman.tuiniverse.logic.commands.EditLessonCommand.EditLessonDescriptor;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX, PREFIX_LESSON_INDEX, PREFIX_DAY, PREFIX_SUBJECT,
                        PREFIX_LEVEL, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_RATE);
        Index studentIndex;
        Index lessonIndex;

        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
        } catch (NoSuchElementException e) {
            // I don't know what the error message will look like
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE), e);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE), e);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_INDEX, PREFIX_LESSON_INDEX, PREFIX_DAY, PREFIX_SUBJECT,
                PREFIX_LEVEL, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_RATE);

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();

        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            editLessonDescriptor.setDay(ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editLessonDescriptor.setSubject(ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            editLessonDescriptor.setLevel(ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get()));
        }
        if (argMultimap.getValue(PREFIX_RATE).isPresent()) {
            editLessonDescriptor.setRate(ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get()));
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent() && argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            editLessonDescriptor.setLessonTime(
                    ParserUtil.parseLessonTime(
                            argMultimap.getValue(PREFIX_START_TIME).get(),
                            argMultimap.getValue(PREFIX_END_TIME).get()));
        } else if ((argMultimap.getValue(PREFIX_START_TIME).isPresent()
                && !argMultimap.getValue(PREFIX_END_TIME).isPresent())
                || (!argMultimap.getValue(PREFIX_START_TIME).isPresent()
                && argMultimap.getValue(PREFIX_END_TIME).isPresent())) {
            // checks that if one is present, both are present
            // may have to change error message
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE));
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLessonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLessonCommand(studentIndex, lessonIndex, editLessonDescriptor);

    }
}
