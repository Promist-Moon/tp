package seedu.address.model.lesson;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Lesson {
    private String subject;
    private int level;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private float hourlyRate;
    private Address location;
    private Duration duration;

    public Lesson(Person person, String subject, int level, DayOfWeek day, LocalTime startTime, LocalTime endTime,
                  float hourlyRate) {
        this.subject = subject;
        assert level < 6 : "Level must be smaller than 6 but was: " + level;
        assert level > 0 : "Level must be larger than 0 but was: " + level;
        this.level = level;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hourlyRate = hourlyRate;
        this.location = person.getAddress();
        this.duration = Duration.between(startTime, endTime);
    }

}
