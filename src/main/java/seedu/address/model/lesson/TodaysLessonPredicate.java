package seedu.address.model.lesson;

import java.time.DayOfWeek;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TodaysLessonPredicate implements Predicate<Lesson> {
    private final DayOfWeek day;

    public TodaysLessonPredicate(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.getDay().getDayOfWeek().equals(day);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodaysLessonPredicate)) {
            return false;
        }

        TodaysLessonPredicate otherTodaysLessonPredicate = (TodaysLessonPredicate) other;
        return day.equals(otherTodaysLessonPredicate.getDay());
    }

    public DayOfWeek getDay() {
        return day;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("day: ", day).toString();
    }
}
