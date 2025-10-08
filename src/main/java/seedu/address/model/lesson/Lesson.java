package seedu.address.model.lesson;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.*;

import java.time.Duration;
import java.util.Objects;

public class Lesson {
    private Person student;
    private Subject subject;
    private Level level;
    private Day day;
    private StartTime startTime;
    private EndTime endTime;
    private Rate rate;
    private Address address;
    private Duration duration;

    public Lesson(Subject subject, Level level, Day day, StartTime startTime, EndTime endTime,
                  Rate rate) {
        this.subject = subject;
        this.level = level;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.duration = endTime.getDuration();
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

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Rate getRate() {
        return rate;
    }

    public Address getAddress() {
        return address;
    }

    public Duration getDuration() {
        return duration;
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
                && endTime.hasTimeClash(otherLesson.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject, level, day, startTime, endTime, rate, address, duration);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .add("level", level)
                .add("day", day)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("rate", rate)
                .add("address", address)
                .toString();
    }

}
