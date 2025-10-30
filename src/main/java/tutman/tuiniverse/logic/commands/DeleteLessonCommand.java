package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;

import java.util.List;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonList;
import tutman.tuiniverse.model.lesson.exceptions.LessonException;
import tutman.tuiniverse.model.student.Student;

/**
 * Represents a command to delete a lesson from a student's lesson list.
 * This command deletes the lesson identified by the index number
 * used in the displayed lesson list for the student identified by the index number
 * used in the displayed student list.
 * Usage example:
 *     delete.lesson s/1 l/2
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "delete.lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by the index number used in the displayed lesson list"
            + " for the student identified by the index number used in the displayed student list.\n"
            + "Parameters: STUDENT_INDEX (must be a positive integer)"
            + "LESSON_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_LESSON_INDEX + "2";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    private final Index studentIndex;
    private final Index lessonIndex;

    /**
     * Constructs a DeleteLessonCommand with given student and lesson indexes.
     *
     * @param sI Index of the student in the displayed list.
     * @param lI Index of the lesson in the student's lesson list.
     */
    public DeleteLessonCommand(Index sI, Index lI) {
        this.studentIndex = sI;
        this.lessonIndex = lI;
    }

    /**
     * Executes the DeleteLessonCommand.
     * Attempts to delete the lesson from the specified student's lesson list.
     *
     * @param model the {@link Model} which the command should operate on
     * @return the result of command execution
     * @throws CommandException if the provided indexes are invalid,
     *         or if the lesson deletion fails
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> studentList = model.getFilteredPersonList();

        if (studentIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student student = studentList.get(studentIndex.getZeroBased());
        LessonList lls = student.getLessonList();

        if (lessonIndex.getOneBased() > lls.getSize()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        }

        try {
            Lesson deleteLesson = lls.getLesson(lessonIndex.getOneBased());
            model.deleteLesson(student, deleteLesson);
            return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, Messages.formatLesson(deleteLesson)));
        } catch (LessonException e) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        }

    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        DeleteLessonCommand otherDeleteLessonCommand = (DeleteLessonCommand) other;
        return studentIndex.equals(otherDeleteLessonCommand.studentIndex)
                && lessonIndex.equals(otherDeleteLessonCommand.lessonIndex);
    }


}
