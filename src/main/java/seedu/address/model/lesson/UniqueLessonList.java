package seedu.address.model.lesson;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.YearMonth;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.util.DateTimeUtil;

/**
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
 * A Lesson is considered unique by comparing using {@code Lesson#equals(Lesson)}. As such, adding and updating of
 * lessons uses Lesson#equals(Lesson) for equality so as to ensure that the lesson being added or updated is
 * unique in terms of identity in the UniqueLessonList. However, the removal of a lesson uses Lesson#equals(object) so
 * as to ensure that the lesson with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueLessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Checks if the specified lesson has time clashes with any lessons in this unique lesson list.
     *
     * <p>Iterates through all lessons starting from index 1 up to the size of the list,
     * and compares each lesson with the given lesson for time clashes.</p>
     *
     * @param lesson the Lesson to check for existence in this list
     * @return true if the lesson is found in the list; false otherwise or if an exception occurs
     */
    public boolean hasTimeClash(Lesson lesson) {
        for (Lesson lesson1 : internalList) {
            if (lesson1.hasTimeClash(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd) || hasTimeClash(toAdd)) {
            // to throw separate exception??
            throw new DuplicateLessonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the list.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the list.
     */
    public void setLesson(Lesson target, Lesson editedLesson) throws DuplicateLessonException {
        requireAllNonNull(target, editedLesson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.equals(editedLesson) && contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        internalList.set(index, editedLesson);
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LessonNotFoundException();
        }
    }

    public void setLessons(UniqueLessonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }

        internalList.setAll(lessons);
    }

    /**
     * Returns the total amount earned per month for a list of lessons
     *
     * @return float representing the sum of all amounts earned per lesson.
     */
    public float getTotalAmountEarned() {
        YearMonth current = DateTimeUtil.currentYearMonth();
        float totalAmountEarned = 0;
        for (Lesson l : internalList) {
            Day day = l.getDay();

            // count number of lessons in a month based on local month
            int daysInMonth = DateTimeUtil.countDaysOfWeekInMonth(current, day);

            float amountPerLesson = l.getAmountEarned();
            float amountPerMonth = daysInMonth * amountPerLesson;

            totalAmountEarned += amountPerMonth;
        }
        return totalAmountEarned;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueLessonList)) {
            return false;
        }

        UniqueLessonList otherUniqueLessonList = (UniqueLessonList) other;
        return internalList.equals(otherUniqueLessonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean lessonsAreUnique(List<Lesson> lessons) {
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).equals(lessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
