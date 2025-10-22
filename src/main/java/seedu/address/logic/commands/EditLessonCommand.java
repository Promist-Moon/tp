package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonException;
import seedu.address.model.person.Person;
import seedu.address.model.person.student.Student;

/**
 * Edits the details of an existing lesson in the address book.
 * Replaces the original lesson in the student lesson list and the unique lesson list in the addressbook with
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "edit.lesson";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson clashes with an existing "
            + "lesson in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index studentIndex;
    private final Index lessonIndex;
    // private final EditLessonDescriptor editLessonDescriptor;

    public EditLessonCommand(Index studentIndex, Index lessonIndex) {
        requireAllNonNull(studentIndex, lessonIndex);

        this.studentIndex = studentIndex;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        // Initialising student
        Student student;

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(studentIndex.getZeroBased());

        if (person instanceof Student) {
            student = (Student) person;
        } else {
            // might have to change to person is not a student
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Initialising student's lesson list
        LessonList studentLessonList = student.getLessonList();

        if (lessonIndex.getOneBased() > studentLessonList.getSize()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        }

        // Handling logic
        try {
            Lesson lessonToEdit = studentLessonList.getLesson(lessonIndex.getZeroBased());
            Lesson editedLesson;

            model.setLesson(student, lessonToEdit, editedLesson);
            return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson)));

        } catch (LessonException e) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }
    }

}
