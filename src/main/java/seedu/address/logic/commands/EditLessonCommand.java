package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.LessonTime;
import seedu.address.model.lesson.Level;
import seedu.address.model.lesson.Rate;
import seedu.address.model.lesson.Subject;
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
    private final EditLessonDescriptor editLessonDescriptor;

    public EditLessonCommand(Index studentIndex, Index lessonIndex, EditLessonDescriptor editLessonDescriptor) {
        requireAllNonNull(studentIndex, lessonIndex, editLessonDescriptor);

        this.studentIndex = studentIndex;
        this.lessonIndex = lessonIndex;
        this.editLessonDescriptor = editLessonDescriptor;
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
            Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

            model.setLesson(student, lessonToEdit, editedLesson);
            return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.formatLesson(editedLesson)));

        } catch (LessonException e) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }
    }

    /**
     * Handles editing a {@code Lesson} edited with {@code editLessonDescriptor}.
     */
    private static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;
        Day updatedDay = editLessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        LessonTime updatedLessonTime = editLessonDescriptor.getLessonTime().orElse(lessonToEdit.getLessonTime());
        Level updatedLevel = editLessonDescriptor.getLevel().orElse(lessonToEdit.getLevel());
        Rate updatedRate = editLessonDescriptor.getRate().orElse(lessonToEdit.getRate());
        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());
        Student student = lessonToEdit.getStudent();
        Lesson updatedLesson = new Lesson(updatedSubject, updatedLevel, updatedDay, updatedLessonTime, updatedRate);
        updatedLesson.addStudent(student);
        return updatedLesson;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        EditLessonCommand otherEditLessonCommand = (EditLessonCommand) other;
        return studentIndex.equals(otherEditLessonCommand.studentIndex)
                && lessonIndex.equals(otherEditLessonCommand.lessonIndex)
                && editLessonDescriptor.equals(otherEditLessonCommand.editLessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("lessonIndex", lessonIndex)
                .add("editLessonDescriptor", editLessonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditLessonDescriptor {
        private Day day;
        private LessonTime lessonTime;
        private Level level;
        private Rate rate;
        private Subject subject;

        public EditLessonDescriptor() {
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(day, lessonTime, level, rate, subject);
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public Optional<Day> getDay() {
            return Optional.ofNullable(day);
        }

        public void setLessonTime(LessonTime lessonTime) {
            this.lessonTime = lessonTime;
        }

        public Optional<LessonTime> getLessonTime() {
            return Optional.ofNullable(lessonTime);
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public Optional<Level> getLevel() {
            return Optional.ofNullable(level);
        }

        public void setRate(Rate rate) {
            this.rate = rate;
        }

        public Optional<Rate> getRate() {
            return Optional.ofNullable(rate);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            EditLessonDescriptor otherEditLessonDescriptor = (EditLessonDescriptor) other;
            return Objects.equals(day, otherEditLessonDescriptor.day)
                    && Objects.equals(lessonTime, otherEditLessonDescriptor.lessonTime)
                    && Objects.equals(level, otherEditLessonDescriptor.level)
                    && Objects.equals(rate, otherEditLessonDescriptor.rate)
                    && Objects.equals(subject, otherEditLessonDescriptor.subject);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("day", day)
                    .add("lessonTime", lessonTime)
                    .add("level", level)
                    .add("rate", rate)
                    .add("subject", subject)
                    .toString();

        }
    }
}
