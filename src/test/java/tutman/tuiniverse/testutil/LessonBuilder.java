package tutman.tuiniverse.testutil;

import static tutman.tuiniverse.model.lesson.LessonTime.ofLessonTime;

import java.time.LocalTime;

import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;
import tutman.tuiniverse.model.student.Address;
import tutman.tuiniverse.model.student.Student;

/**
 * A utility class to help with building {@link Lesson} objects for testing.
 * Typical usage example:
 * {@code
 * Lesson lesson = new LessonBuilder()
 *         .withSubject("Math")
 *         .withLevel("2")
 *         .withDay("1")
 *         .withLessonTime("10:00", "12:00")
 *         .withRate("30")
 *         .withStudent(new StudentBuilder().build())
 *         .build();
 * }
 */
public class LessonBuilder {

    //Default field values for convenience in tests
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

    /**
     * Creates a {@code LessonBuilder} with default values.
     *   Subject: "Math"
     *   Level: "2"
     *   Day: "1" (Monday)
     *   Lesson time: 10:00–12:00
     *   Rate: "30"
     */
    public LessonBuilder() {
        subject = Subject.fromString(DEFAULT_SUBJECT);
        level = Level.fromString(DEFAULT_LEVEL);
        day = new Day(Integer.valueOf(DEFAULT_DAY));
        lt = ofLessonTime(DEFAULT_START_TIME, DEFAULT_END_TIME);
        rate = new Rate(DEFAULT_RATE);
        student = null;
        address = null;
    }

    /**
     * Initializes the builder with the data of an existing {@code Lesson}.
     *
     * @param lessonToCopy the lesson whose fields to copy.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        subject = lessonToCopy.getSubject();
        level = lessonToCopy.getLevel();
        day = lessonToCopy.getDay();
        lt = lessonToCopy.getLessonTime();
        rate = lessonToCopy.getRate();
        student = lessonToCopy.getStudent();
        address = lessonToCopy.getAddress();
    }

    /**
     * Sets the {@link Subject} of the {@link Lesson} to build.
     *
     * @param subject a valid subject name (case-insensitive).
     * @return this builder for chaining.
     */
    public LessonBuilder withSubject(String subject) {
        this.subject = Subject.fromString(subject);
        return this;
    }

    /**
     * Sets the {@link Level} of the {@link Lesson} to build.
     *
     * @param level a valid level string (e.g., "1"–"5").
     * @return this builder for chaining.
     */
    public LessonBuilder withLevel(String level) {
        this.level = Level.fromString(level);
        return this;
    }

    /**
     * Sets the {@link Day} of the {@link Lesson} to build.
     *
     * @param day a string between "1" and "7" representing Monday–Sunday.
     * @return this builder for chaining.
     */
    public LessonBuilder withDay(String day) {
        this.day = new Day(Integer.valueOf(day));
        return this;
    }

    /**
     * Sets the {@link LessonTime} of the {@link Lesson} to build.
     *
     * @param start start time string in 24-hour format (HH:mm).
     * @param end   end time string in 24-hour format (HH:mm).
     * @return this builder for chaining.
     */
    public LessonBuilder withLessonTime(String start, String end) {
        this.lt = ofLessonTime(start, end);
        return this;
    }

    /**
     * Sets the {@link Rate} (hourly rate) of the {@link Lesson} to build.
     *
     * @param rate rate string (numeric, non-negative).
     * @return this builder for chaining.
     */
    public LessonBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    /**
     * Associates a {@link Student} with this lesson.
     *
     *
     * @param student the student to associate.
     * @return this builder for chaining.
     */
    public LessonBuilder withStudent(Student student) {
        this.student = student;

        if (student != null) {
            this.address = student.getAddress();
        }
        return this;
    }

    /**
     * Associates a {@link Address} with this lesson.
     * The student's address is automatically recorded in the builder.
     *
     * @param address the address to associate.
     * @return this builder for chaining.
     */
    public LessonBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Builds and returns a {@link Lesson} with the current builder values.
     * <p>
     * If a {@link Student} was previously set, the student and their
     * {@link Address} are attached via {@link Lesson#addStudent(Student)}.
     *
     * @return a fully constructed {@link Lesson} instance.
     */
    public Lesson build() {
        Lesson lesson = new Lesson(subject, level, day, lt, rate);
        if (student != null) {
            lesson.addStudent(student);
        }
        return lesson;
    }
}
