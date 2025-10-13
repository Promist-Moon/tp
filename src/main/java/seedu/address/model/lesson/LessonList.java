package seedu.address.model.lesson;

import java.time.YearMonth;
import java.util.ArrayList;

import seedu.address.model.lesson.exceptions.LessonException;
import seedu.address.model.util.DateTimeUtil;

/**
 * Represents a list of lessons.
 * A LessonList represents the lessons one student takes under one tutor.
 * It supports adding, retrieving, deleting, and printing lessons.
 */
public class LessonList {
    private final ArrayList<Lesson> lessons;

    /**
     * Constructs a new lesson list by creating an empty array list.
     */
    public LessonList() {
        this.lessons = new ArrayList<>();
    }

    public int getSize() {
        return lessons.size();
    }

    public Lesson getLesson(int indexOneBased) throws LessonException {
        int idx = indexOneBased - 1;
        if (idx < 0 || idx >= lessons.size()) {
            throw new LessonException("No such lesson: " + indexOneBased);
        }
        return lessons.get(idx);
    }

    /**
     * Adds a new lesson to the lesson list.
     */
    public void addLesson(Subject subject, Level level, Day day, LessonTime lessonTime, Rate rate) {
        lessons.add(new Lesson(subject, level, day, lessonTime, rate));
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    /**
     * Returns a string representation of the lesson list, with
     * each lesson prefixed by its index.
     *
     * @return the formatted string representation of the lesson list.
     */
    public String getListString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(i + 1).append(". ").append(lessons.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the total duration of all classes in a month.
     */
    public long getTotalHours(YearMonth month) {
        long totalHours = 0;
        for (Lesson l : lessons) {
            Day day = l.getDay();

            // count number of lessons in a month based on local month
            int daysInMonth = DateTimeUtil.countDaysOfWeekInMonth(month, day);
            long hoursPerLesson = l.getDurationLong();
            long hoursPerMonth = daysInMonth * hoursPerLesson;

            totalHours += hoursPerMonth;
        }
        return totalHours;
    }
}
