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

    /**
     * Adds a new lesson to the lesson list.
     * Overloaded method to take in current lesson instead of creating new lesson object.
     */
    public void addLesson(Lesson lesson) {
        if (!hasLesson(lesson)) {
            lessons.add(lesson);
        }
    }

    /**
     * Deletes a lesson from the lesson List.
     * @param lesson
     */
    public void deleteLesson(Lesson lesson) {
        int ind = lessons.indexOf(lesson);
        if (ind != -1) {
            lessons.remove(ind);
        }
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
     * Returns the total duration of {@code Lesson} hours in a month.
     * @param month of the query.
     * @return the total number of {@code Lesson} hours.
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

    /**
     * Checks if the specified lesson exists in this lesson list.
     *
     * <p>Iterates through all lessons starting from index 1 up to the size of the list,
     * and compares each lesson with the given lesson for equality.</p>
     *
     * @param lesson the Lesson to check for existence in this list
     * @return true if the lesson is found in the list; false otherwise or if an exception occurs
     */
    public boolean hasLesson(Lesson lesson) {
        int i = 1;
        while (i <= getSize()) {
            try {
                if (this.getLesson(i).equals(lesson)) {
                    return true;
                } else i++;
            } catch (LessonException e) {
                return false;
            }
        }
        return false;
    }
}
