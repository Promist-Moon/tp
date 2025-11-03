package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_DAY;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_END_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LEVEL;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_RATE;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_START_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.commons.util.ToStringBuilder;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.exceptions.DuplicateLessonException;
import tutman.tuiniverse.model.student.Student;

/**
 * Adds a lesson to the address book.
 */
public class AddLessonCommand extends Command {
    public static final String COMMAND_WORD = "add.lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the student. \n"
            + "Parameters: "
            + PREFIX_STUDENT_INDEX + "STUDENT INDEX "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_LEVEL + "LEVEL "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "START "
            + PREFIX_END_TIME + "END "
            + PREFIX_RATE + "RATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_SUBJECT + "English "
            + PREFIX_LEVEL + "2 "
            + PREFIX_DAY + "Monday "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "12:00 "
            + PREFIX_RATE + "80 ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This timeslot is already taken";

    private final Lesson toAdd;
    private final Index targetIndex;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Index index, Lesson lesson) {
        requireNonNull(lesson);
        this.targetIndex = index;
        toAdd = lesson;
    }

    // student and address fields should be set here...
    // needs to check if person at index is a student

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }
        List<Student> lastShownList = model.getFilteredPersonList();

        // need to check if index is a student
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        try {
            Student person = lastShownList.get(targetIndex.getZeroBased());
            model.addLesson(person, toAdd);

            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatLesson(toAdd)));
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }

}
