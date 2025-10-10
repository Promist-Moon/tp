package seedu.address.testutil;

import java.time.LocalTime;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Subject;
import seedu.address.model.lesson.Level;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.LessonTime;
import seedu.address.model.lesson.Rate;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.Address;

import static seedu.address.model.lesson.LessonTime.ofLessonTime;

public class LessonBuilder {
    public static final String DEFAULT_SUBJECT = "Math";
    public static final String DEFAULT_LEVEL = "2";
    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_START_TIME = LocalTime.of(10, 0).toString();
    public static final String DEFAULT_END_TIME = LocalTime.of(12, 0).toString();
    public static final String DEFAULT_RATE = "30";

    private Subject subject;
    private Level level;
    private Day day;
    private LessonTime lt;
    private Rate rate;
    private Student student;
    private Address address;

    public LessonBuilder() {
        subject = Subject.fromString(DEFAULT_SUBJECT);
        level = Level.fromString(DEFAULT_LEVEL);
        day = new Day(DEFAULT_DAY);
        lt = ofLessonTime(DEFAULT_START_TIME, DEFAULT_END_TIME);
        rate = new Rate(DEFAULT_RATE);
        student = null;
        address = null;
    }

    public LessonBuilder(Lesson lessonToCopy) {
        subject = lessonToCopy.getSubject();
        level = lessonToCopy.getLevel();
        day = lessonToCopy.getDay();
        lt = lessonToCopy.getLessonTime();
        rate = lessonToCopy.getRate();
        student = lessonToCopy.getStudent();
        address = lessonToCopy.getAddress();
    }

    public LessonBuilder withSubject(String subject) {
        this.subject = Subject.fromString(subject);
        return this;
    }

    public LessonBuilder withLevel(String level) {
        this.level = Level.fromString(level);
        return this;
    }

    public LessonBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    public LessonBuilder withLessonTime(String start, String end) {
        this.lt = ofLessonTime(start, end);
        return this;
    }

    public LessonBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    public LessonBuilder withStudent(Student student) {
        this.student = student;

        if (student != null) {
            this.address = student.getAddress();
        }
        return this;
    }

    public Lesson build() {
        Lesson lesson = new Lesson(subject, level, day, lt, rate);
        if (student != null) {
            lesson.addStudent(student);
        }
        return lesson;
    }

}
