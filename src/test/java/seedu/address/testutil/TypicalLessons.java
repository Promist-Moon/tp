package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@link Lesson} objects to be used in tests.
 */
public class TypicalLessons {
    public static final Lesson Y3_MATH = new LessonBuilder()
            .withSubject("Math")
            .withDay("3")
            .withLessonTime("09:00", "11:00")
            .build();

    public static final Lesson Y1_PHYSICS = new LessonBuilder()
            .withSubject("Physics")
            .withDay("1")
            .withLessonTime("10:00", "11:30")
            .build();

    public static final Lesson Y4_ENGLISH = new LessonBuilder()
            .withSubject("English")
            .withDay("4")
            .withLessonTime("09:30", "11:00")
            .build();

    public static final Lesson Y2_CHEMISTRY = new LessonBuilder()
            .withSubject("Chemistry")
            .withDay("2")
            .withLessonTime("19:30", "21:00")
            .build();

    public static final Lesson Y1_ENGLISH = new LessonBuilder()
            .withSubject("English")
            .withDay("1")
            .withLessonTime("12:30", "14:00")
            .build();

    public static final Lesson Y3_HISTORY = new LessonBuilder()
            .withSubject("History")
            .withDay("3")
            .withLessonTime("16:30", "18:00")
            .build();

    public static final Lesson Y3_GEOGRAPHY = new LessonBuilder()
            .withSubject("History")
            .withDay("5")
            .withLessonTime("19:30", "21:00")
            .build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns a list of typical lessons.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(
                Y3_MATH, Y1_PHYSICS, Y4_ENGLISH,
                Y2_CHEMISTRY, Y1_ENGLISH, Y3_HISTORY,
                Y3_GEOGRAPHY));
    }
}
