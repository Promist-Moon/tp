package seedu.address.testutil;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonTime;
import seedu.address.model.lesson.Level;
import seedu.address.model.lesson.Rate;
import seedu.address.model.lesson.Subject;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.student.Address;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_SUBJECT= "Geography";
    public static final String DEFAULT_LEVEL = "2";
    public static final String DEFAULT_DAY = "3";
    public static final String DEFAULT_START_TIME = "08:30";
    public static final String DEFAULT_END_TIME = "13:30";
    public static final String DEFAULT_RATE = "69.69";

    private Subject subject;
    private Level level;
    private Day day;
    private LessonTime lessonTime;
    private Rate rate;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        subject = Subject.fromString(DEFAULT_SUBJECT);
        level = Level.fromString(DEFAULT_LEVEL);
        day = new Day(DEFAULT_DAY);
        lessonTime = LessonTime.ofLessonTime(DEFAULT_START_TIME, DEFAULT_END_TIME);
        rate = new Rate(DEFAULT_RATE);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        subject = lessonToCopy.getSubject();
        level = lessonToCopy.getLevel();
        day = lessonToCopy.getDay();
        lessonTime = lessonToCopy.getLessonTime();
        rate = lessonToCopy.getRate();
    }

    /**
     * Sets the {@code subject} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withSubject(String subject) {
        this.subject = Subject.fromString(subject);
        return this;
    }

    /**
     * Sets the {@code level} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLevel(String level) {
        this.level = Level.fromString(level);
        return this;
    }

    /**
     * Sets the {@code day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code lessonTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLessonTime(String startTime, String endTime) {
        this.lessonTime = LessonTime.ofLessonTime(startTime, endTime);
        return this;
    }

    /**
     * Sets the {@code rate} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    public Lesson build() {
        return new Lesson(subject, level, day, lessonTime, rate);
    }
}
