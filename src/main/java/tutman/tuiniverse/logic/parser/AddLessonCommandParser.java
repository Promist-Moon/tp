package tutman.tuiniverse.logic.parser;

import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_DAY;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_END_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LEVEL;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_RATE;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_START_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.stream.Stream;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.commands.AddLessonCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_DAY,
                        PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_RATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_INDEX, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_DAY,
                PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_RATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_INDEX, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_DAY,
                PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_RATE);

        // to change Person to Student
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        Level level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        String startTime = argMultimap.getValue(PREFIX_START_TIME).get();
        String endTime = argMultimap.getValue(PREFIX_END_TIME).get();
        LessonTime lessonTime = ParserUtil.parseLessonTime(startTime, endTime);
        Rate rate = ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get());


        Lesson lesson = new Lesson(subject, level, day, lessonTime, rate);

        return new AddLessonCommand(index, lesson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
