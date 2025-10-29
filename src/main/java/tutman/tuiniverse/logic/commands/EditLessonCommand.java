package tutman.tuiniverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutman.tuiniverse.commons.util.CollectionUtil.requireAllNonNull;
import static tutman.tuiniverse.logic.Messages.MESSAGE_INVALID_DISPLAYED_LESSON_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_DAY;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_END_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_LEVEL;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_RATE;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_START_TIME;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutman.tuiniverse.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import tutman.tuiniverse.commons.core.index.Index;
import tutman.tuiniverse.commons.util.CollectionUtil;
import tutman.tuiniverse.commons.util.ToStringBuilder;
import tutman.tuiniverse.logic.Messages;
import tutman.tuiniverse.logic.commands.exceptions.CommandException;
import tutman.tuiniverse.model.Model;
import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonList;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;
import tutman.tuiniverse.model.lesson.exceptions.DuplicateLessonException;
import tutman.tuiniverse.model.lesson.exceptions.LessonException;
import tutman.tuiniverse.model.person.Person;
import tutman.tuiniverse.model.person.student.Student;

/**
 * Edits the details of an existing lesson in the address book.
 * Replaces the original lesson in the student lesson list and the unique lesson list in the addressbook with
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "edit.lesson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson of the student "
            + "identified by the index number used in the displayed person list."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_STUDENT_INDEX + "STUDENT INDEX "
            + PREFIX_LESSON_INDEX + "LESSON INDEX (must be a positive integers)"
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_LEVEL + "LEVEL] "
            + "[" + PREFIX_RATE + "RATE] "
            + "[" + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_STUDENT_INDEX + "1 " + PREFIX_LESSON_INDEX + "2 "
            + PREFIX_DAY + "5 "
            + PREFIX_RATE + "44";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson clashes with an existing "
            + "lesson in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index studentIndex;
    private final Index lessonIndex;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * @param studentIndex of the student in the filtered person list whose lesson to edit
     * @param lessonIndex of the lesson in the displayed lesson list of the student to edit
     * @param editLessonDescriptor details to edit the lesson with
     */
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
            Lesson lessonToEdit = studentLessonList.getLesson(lessonIndex.getOneBased());
            Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

            model.setLesson(student, lessonToEdit, editedLesson);
            Predicate<Lesson> belongsToStudent =
                    lesson -> student.getLessonList().hasLesson(lesson);
            model.updateFilteredLessonList(belongsToStudent);

            return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(student)));

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
