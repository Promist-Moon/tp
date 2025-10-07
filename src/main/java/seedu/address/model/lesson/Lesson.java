package seedu.address.model.lesson;

import seedu.address.model.person.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Lesson {
    private Subject subject;
    private Level level;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Rate rate;
    private Address address;
    private Duration duration;

    public Lesson(Person person, Subject subject, Level level, DayOfWeek day, LocalTime startTime, LocalTime endTime,
                  Rate rate) {
        this.subject = subject;
        this.level = level;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.address = person.getAddress();
        this.duration = Duration.between(startTime, endTime);
    }

    public Subject getSubject() {
        return subject;
    }

    public Level getLevel() {
        return level;
    }

    public DayOfWeek getDay() {
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

}
