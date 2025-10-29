package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.commons.util.ToStringBuilder;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.person.Person;
import tutman.tuiniverse.model.person.student.Student;

/**
 * Views all lessons taken by a student.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the lessons taken by the specfied student,"
            + " using their index number shown in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_VIEW_SUCCESS = "Lessons for student: %1$s";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToViewLessons = (Student) lastShownList.get(targetIndex.getZeroBased());

        Predicate<Lesson> belongsToStudent =
                lesson -> studentToViewLessons.getLessonList().hasLesson(lesson);
        model.updateFilteredLessonList(belongsToStudent);

        return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, Messages.format(studentToViewLessons)),
                true, studentToViewLessons.getName().toString());


    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
