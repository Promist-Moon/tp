package tutman.tuiniverse.logic.parser;

import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_NAME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import tutman.tuiniverse.logic.commands.AddCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.student.Address;
import tutman.tuiniverse.model.student.Email;
import tutman.tuiniverse.model.student.Name;
import tutman.tuiniverse.model.student.Phone;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.model.student.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Student student = new Student(name, phone, email, address, tagList);

        return new AddCommand(student);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
