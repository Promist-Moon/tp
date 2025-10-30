package tutman.tuiniverse.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutman.tuiniverse.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tutman.tuiniverse.testutil.Assert.assertThrows;
import static tutman.tuiniverse.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tutman.tuiniverse.logic.commands.AddCommand;
import tutman.tuiniverse.logic.commands.ClearCommand;
import tutman.tuiniverse.logic.commands.DeleteCommand;
import tutman.tuiniverse.logic.commands.EditCommand;
import tutman.tuiniverse.logic.commands.EditCommand.EditPersonDescriptor;
import tutman.tuiniverse.logic.commands.ExitCommand;
import tutman.tuiniverse.logic.commands.FindCommand;
import tutman.tuiniverse.logic.commands.HelpCommand;
import tutman.tuiniverse.logic.commands.ListCommand;
import tutman.tuiniverse.logic.commands.ListOverdueCommand;
import tutman.tuiniverse.logic.commands.ListPaidCommand;
import tutman.tuiniverse.logic.commands.ListUnpaidCommand;
import tutman.tuiniverse.logic.commands.PayCommand;
import tutman.tuiniverse.logic.parser.exceptions.ParseException;
import tutman.tuiniverse.model.student.NameContainsKeywordsPredicate;
import tutman.tuiniverse.model.student.Student;
import tutman.tuiniverse.testutil.EditPersonDescriptorBuilder;
import tutman.tuiniverse.testutil.StudentBuilder;
import tutman.tuiniverse.testutil.StudentUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student person = new StudentBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + StudentUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_pay() throws Exception {
        PayCommand command = (PayCommand) parser.parseCommand(
                PayCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PayCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listUnpaid() throws Exception {
        assertTrue(parser.parseCommand(ListUnpaidCommand.COMMAND_WORD) instanceof ListUnpaidCommand);
        assertTrue(parser.parseCommand(ListUnpaidCommand.COMMAND_WORD + " 3") instanceof ListUnpaidCommand);
    }

    @Test
    public void parseCommand_listPaid() throws Exception {
        assertTrue(parser.parseCommand(ListPaidCommand.COMMAND_WORD) instanceof ListPaidCommand);
        assertTrue(parser.parseCommand(ListPaidCommand.COMMAND_WORD + " 3") instanceof ListPaidCommand);
    }

    @Test
    public void parseCommand_listOverdue() throws Exception {
        assertTrue(parser.parseCommand(ListOverdueCommand.COMMAND_WORD) instanceof ListOverdueCommand);
        assertTrue(parser.parseCommand(ListOverdueCommand.COMMAND_WORD + " 3") instanceof ListOverdueCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
