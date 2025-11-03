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
import tutman.tuiniverse.model.student.Student;

/**
 * Edits the details of an existing lesson in the address book.
 * Replaces the original lesson in the student lesson list and the unique lesson list in the addressbook with
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "edit.lesson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson of the student "
            + "identified by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n \n"
            + "Parameters: " + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_LEVEL + "LEVEL] "
            + "[" + PREFIX_RATE + "RATE] "
            + "[" + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME]...\n"
            + "(STUDENT_INDEX and LESSON_INDEX must be positive integers.)\n"
            + "(To change the lesson time, both START TIME and END TIME are needed.)\n \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_STUDENT_INDEX + "1 " + PREFIX_LESSON_INDEX + "2 "
            + PREFIX_DAY + "Monday "
            + PREFIX_RATE + "44";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson clashes with an existing "
            + "lesson in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String RATE_FIELD_EMPTY = "Rate cannot be empty. Choose any number bigger than or equal to 0.";
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
        List<Student> lastShownList = model.getFilteredPersonList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student person = lastShownList.get(studentIndex.getZeroBased());
        LessonList studentLessonList = person.getLessonList();

        if (lessonIndex.getOneBased() > studentLessonList.getSize()) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        }

        // Handling logic
        try {
            Lesson lessonToEdit = studentLessonList.getLesson(lessonIndex.getOneBased());
            Lesson editedLesson = createEditedLesson(person, lessonToEdit, editLessonDescriptor);
            editedLesson.addStudent(person);
            model.setLesson(person, lessonToEdit, editedLesson);
            Predicate<Lesson> belongsToStudent =
                    lesson -> person.getLessonList().hasLesson(lesson);
            model.updateFilteredLessonList(belongsToStudent);

            return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.formatLesson(editedLesson)),
                    true, person.getName().toString());

        } catch (LessonException e) {
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_LESSON_INDEX);
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (NumberFormatException e) {
            throw new CommandException(RATE_FIELD_EMPTY);
        }
    }

    /**
     * Handles editing a {@code Lesson} edited with {@code editLessonDescriptor}.
     */
    private static Lesson createEditedLesson(Student student, Lesson lessonToEdit,
                                             EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;
        Day updatedDay = editLessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        LessonTime updatedLessonTime = editLessonDescriptor.getLessonTime().orElse(lessonToEdit.getLessonTime());
        Level updatedLevel = editLessonDescriptor.getLevel().orElse(lessonToEdit.getLevel());
        Rate updatedRate = editLessonDescriptor.getRate().orElse(lessonToEdit.getRate());
        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());
        Lesson updatedLesson = new Lesson(updatedSubject, updatedLevel, updatedDay,
                updatedLessonTime, updatedRate);
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
        private String studentName;

        public EditLessonDescriptor() {
        }

        /** Creates a descriptor from String inputs. */
        public EditLessonDescriptor(String day, String startTime, String endTime, String level,
                                    String rate, String subject) {
            this.day = new Day(Integer.valueOf(day));
            this.lessonTime = LessonTime.ofLessonTime(startTime, endTime);
            this.level = Level.fromString(level);
            this.rate = new Rate(rate);
            this.subject = Subject.fromString(subject);
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
            return this.day.equals(otherEditLessonDescriptor.day)
                    && this.lessonTime.equals(otherEditLessonDescriptor.lessonTime)
                    && (this.level.getLevel() == otherEditLessonDescriptor.level.getLevel())
                    && (this.rate.getRate() == otherEditLessonDescriptor.rate.getRate())
                    && this.subject.getSubject().equals(otherEditLessonDescriptor.subject.getSubject());
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

