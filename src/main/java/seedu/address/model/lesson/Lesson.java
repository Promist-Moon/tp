package seedu.address.model.lesson;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class Lesson {
    private Person student;
    private Subject subject;
    private Level level;
    private Day day;
    private LessonTime lessonTime;
    private Rate rate;
    private Address address;

    public Lesson(Subject subject, Level level, Day day, LessonTime lessonTime, Rate rate) {
        this.subject = subject;
        this.level = level;
        this.day = day;
        this.lessonTime = lessonTime;
        this.rate = rate;
    }

    public Subject getSubject() {
        return subject;
    }

    public Level getLevel() {
        return level;
    }

    public Day getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return lessonTime.getStart();
    }

    public LocalTime getEndTime() {
        return lessonTime.getEnd();
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public Rate getRate() {
        return rate;
    }

    public Address getAddress() {
        return address;
    }

    public Duration getDuration() {
        return lessonTime.getDuration();
    }

    public Person getStudent() {
        return student;
    }

    public void addStudent(Person student) {
        this.student = student;
        this.address = student.getAddress();
    }

    /**
     * Returns true if both lessons have the same day and their lesson times clash.
     * This defines a stronger notion of equality between two lessons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return day.getDayOfWeek().equals(otherLesson.day.getDayOfWeek())
                && lessonTime.hasTimeClash(otherLesson.lessonTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject, level, day, lessonTime, rate, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .add("level", level)
                .add("day", day)
                .add("time", lessonTime)
                .add("rate", rate)
                .add("address", address)
                .toString();
    }

}
