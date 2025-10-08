package seedu.address.model.lesson;

import seedu.address.model.person.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
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

}
