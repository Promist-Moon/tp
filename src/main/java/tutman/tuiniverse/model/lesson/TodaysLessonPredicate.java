package tutman.tuiniverse.model.lesson;


import java.util.function.Predicate;

import tutman.tuiniverse.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TodaysLessonPredicate implements Predicate<Lesson> {
    private final Day day;

    public TodaysLessonPredicate(Day day) {
        this.day = day;
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.getDay().equals(day);
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

    public Day getDay() {
        return day;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("day: ", day).toString();
    }
}
