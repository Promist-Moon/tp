package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.util.DateTimeUtil;

/**
 * Represents a list of lessons.
 * A LessonList represents the lessons one student takes under one tutor.
 * It supports adding, retrieving, deleting, and printing lessons.
 */
public class LessonList {
    private final ObservableList<Lesson> lessons;
    private final ObservableList<Lesson> unmodifiableLessons;

    /**
     * Constructs a new lesson list by creating an empty array list.
     */
    public LessonList() {
        this.lessons = FXCollections.observableArrayList();
        this.unmodifiableLessons = FXCollections.unmodifiableObservableList(this.lessons);
    }

    /**
     * Constructs a new lesson list by copying from another LessonList/Collection.
     *
     * @param ll a LessonList/Collection of lessons.
     */
    public LessonList(Collection<Lesson> ll) {
        this.lessons = FXCollections.observableArrayList(Objects.requireNonNull(ll));
        this.unmodifiableLessons = FXCollections.unmodifiableObservableList(this.lessons);
    }

    /*
    Listeners
     */

    /**
     * Subscribes a listener to mutations of the list (add/remove/replace/update).
     *
     * @param listener a listener of any changes to lessons
     */
    public void addListener(ListChangeListener<? super Lesson> listener) {
        lessons.addListener(listener);
    }

    /**
     * Unsubscribes a previously added listener.
     *
     * @param listener a listener of any changes to lessons
     **/
    public void removeListener(ListChangeListener<? super Lesson> listener) {
        lessons.removeListener(listener);
    }

    /*
    Getters
     */
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

    public ArrayList<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    /**
     * Adds a new lesson to the lesson list.
     * Overloaded method to take in current lesson instead of creating new lesson object.
     */
    public LessonList addLesson(Lesson lesson) {
        if (!this.hasLesson(lesson) && !this.hasTimeClash(lesson)) {
            lessons.add(lesson);
        }
        return this;
    }

    /**
     * Deletes a lesson from the lesson List.
     * @param lesson A lesson object
     */
    public void deleteLesson(Lesson lesson) {
        int ind = lessons.indexOf(lesson);
        if (ind != -1) {
            lessons.remove(ind);
        } else {
            throw new LessonNotFoundException();
        }
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the list.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the list.
     *
     * @throws DuplicateLessonException when there are time clashes.
     */
    public void setLesson(Lesson target, Lesson editedLesson) throws DuplicateLessonException {
        requireAllNonNull(target, editedLesson);

        int index = lessons.indexOf(target);

        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.equals(editedLesson) && hasLesson(editedLesson)) {
            throw new DuplicateLessonException();
        }

        lessons.set(index, editedLesson);

    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }


    /**
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
        for (Lesson lesson1 : lessons) {
            if (lesson1.equals(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the specified lesson has time clashes with any lessons in this lesson list.
     *
     * <p>Iterates through all lessons starting from index 1 up to the size of the list,
     * and compares each lesson with the given lesson for time clashes.</p>
     *
     * @param lesson the Lesson to check for existence in this list
     * @return true if the lesson is found in the list; false otherwise or if an exception occurs
     */
    public boolean hasTimeClash(Lesson lesson) {
        for (Lesson lesson1 : lessons) {
            if (lesson1.hasTimeClash(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the total amount earned per month for a list of lessons
     *
     * @param month a YearMonth object, usually current month
     * @return float representing the sum of all amounts earned per lesson.
     */
    public float getTotalAmountEarned(YearMonth month) {
        float totalAmountEarned = 0;
        for (Lesson l : lessons) {
            Day day = l.getDay();

            // count number of lessons in a month based on local month
            int daysInMonth = DateTimeUtil.countDaysOfWeekInMonth(month, day);

            float amountPerLesson = l.getAmountEarned();
            float amountPerMonth = daysInMonth * amountPerLesson;

            totalAmountEarned += amountPerMonth;
        }
        return totalAmountEarned;
    }

    /**
     * Returns a string representation of the lesson list, with
     * each lesson prefixed by its index.
     *
     * @return the formatted string representation of the lesson list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(i + 1).append(". ").append(lessons.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonList)) {
            return false;
        }

        LessonList otherLessonList = (LessonList) other;
        return lessons.equals(otherLessonList.lessons);
    }
}
