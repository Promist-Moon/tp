package tutman.tuiniverse.testutil;

import static java.util.Objects.requireNonNull;

import tutman.tuiniverse.logic.commands.EditLessonCommand;
import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;

/**
 * A utility class to help with building {@link EditLessonCommand.EditLessonDescriptor} objects for tests.
 *
 * Typical usage:
 * <pre>
 *     EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
 *          .withDay(new Day(3))
 *          .withLessonTime(new LessonTime(start, end))
 *          .withLevel(new Level("Sec 3"))
 *          .withRate(new Rate("40"))
 *          .withSubject(new Subject("Math"))
 *          .build();
 * </pre>
 */
public class EditLessonDescriptorBuilder {
    private final EditLessonCommand.EditLessonDescriptor descriptor;

    /** Creates an empty descriptor. */
    public EditLessonDescriptorBuilder() {
        this.descriptor = new EditLessonCommand.EditLessonDescriptor();
    }

    /** Creates a descriptor from String inputs. */
    public EditLessonDescriptorBuilder(String day, String startTime, String endTime, String level,
                                       String rate, String subject) {
        this.descriptor = new EditLessonCommand.EditLessonDescriptor();
        descriptor.setDay(new Day(day));
        descriptor.setLessonTime(LessonTime.ofLessonTime(startTime, endTime));
        descriptor.setLevel(Level.fromString(level));
        descriptor.setRate(new Rate(rate));
        descriptor.setSubject(Subject.fromString(subject));
    }

    /** Copies values from an existing descriptor (defensive copy). */
    public EditLessonDescriptorBuilder(EditLessonCommand.EditLessonDescriptor toCopy) {
        requireNonNull(toCopy);
        this.descriptor = new EditLessonCommand.EditLessonDescriptor();
        toCopy.getDay().ifPresent(descriptor::setDay);
        toCopy.getLessonTime().ifPresent(descriptor::setLessonTime);
        toCopy.getLevel().ifPresent(descriptor::setLevel);
        toCopy.getRate().ifPresent(descriptor::setRate);
        toCopy.getSubject().ifPresent(descriptor::setSubject);
    }

    /** Seeds values from an existing lesson (handy for “edit one field” tests). */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        requireNonNull(lesson);
        this.descriptor = new EditLessonCommand.EditLessonDescriptor();
        descriptor.setDay(lesson.getDay());
        descriptor.setLessonTime(lesson.getLessonTime());
        descriptor.setLevel(lesson.getLevel());
        descriptor.setRate(lesson.getRate());
        descriptor.setSubject(lesson.getSubject());
    }

    public EditLessonDescriptorBuilder withDay(Day day) {
        descriptor.setDay(day);
        return this;
    }

    public EditLessonDescriptorBuilder withLessonTime(LessonTime lessonTime) {
        descriptor.setLessonTime(lessonTime);
        return this;
    }

    public EditLessonDescriptorBuilder withLevel(Level level) {
        descriptor.setLevel(level);
        return this;
    }

    public EditLessonDescriptorBuilder withRate(Rate rate) {
        descriptor.setRate(rate);
        return this;
    }

    public EditLessonDescriptorBuilder withSubject(Subject subject) {
        descriptor.setSubject(subject);
        return this;
    }

    public EditLessonCommand.EditLessonDescriptor build() {
        return descriptor;
    }

}
